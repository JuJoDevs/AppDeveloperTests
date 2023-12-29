package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.usecases.GetPagingUsersUseCase
import com.jujodevs.testshared.LazyPagingItemsTest
import com.jujodevs.testshared.testrules.CoroutinesTestRule
import com.jujodevs.testshared.verifyOnce
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getPagingUsersUseCase: GetPagingUsersUseCase = mockk()

    private lateinit var viewModel: UsersViewModel

    @Test
    fun `GIVEN a flow paging data with one user WHEN collect THEN collect the same user`() =
        runTest {
            val users = buildPagingUserDataFlow(listOf(User(id = 1, name = "test")))
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()
            val items = state.itemSnapshotList.items

            verifyOnce { getPagingUsersUseCase() }
            items[0].id shouldBeEqualTo 1
            items[0].name shouldBeEqualTo "test"
            items.size shouldBeEqualTo 1
        }

    @Test
    fun `GIVEN a flow paging data with users WHEN collect THEN collect the same users`() = runTest {
        val users = buildPagingUserDataFlow(
            listOf(
                User(id = 1, name = "test"),
                User(id = 2, name = "test2"),
            ),
        )
        every { getPagingUsersUseCase() } returns users
        viewModel = UsersViewModel(getPagingUsersUseCase)

        val state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        val items = state.itemSnapshotList.items

        verifyOnce { getPagingUsersUseCase() }
        items[0].id shouldBeEqualTo 1
        items[0].name shouldBeEqualTo "test"
        items[1].id shouldBeEqualTo 2
        items[1].name shouldBeEqualTo "test2"
        items.size shouldBeEqualTo 2
    }

    @Test
    fun `GIVEN a flow paging data without users WHEN collect THEN collect an empty list`() =
        runTest {
            val users = buildPagingUserDataFlow(emptyList())
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()

            verifyOnce { getPagingUsersUseCase() }
            val items = state.itemSnapshotList.items
            items.size shouldBeEqualTo 0
        }

    @Test
    fun `GIVEN a flow paging with state Loading WHEN collect THEN do collect state Loading`() =
        runTest {
            val users = buildPagingUserDataFlow(loadState = LoadState.Loading)
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()

            verifyOnce { getPagingUsersUseCase() }
            state.itemSnapshotList.items.size shouldBeEqualTo 0
            state.loadState.refresh shouldBeInstanceOf LoadState.Loading::class
        }

    @Test
    fun `GIVEN a flow paging with state Error WHEN collect THEN do collect state Error with Throwable`() =
        runTest {
            val users = buildPagingUserDataFlow(loadState = LoadState.Error(Throwable("Error")))
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()

            verifyOnce { getPagingUsersUseCase() }
            state.itemSnapshotList.items.size shouldBeEqualTo 0
            state.loadState.refresh shouldBeInstanceOf LoadState.Error::class
            (state.loadState.refresh as LoadState.Error).error.message shouldBeEqualTo "Error"
        }

    @Test
    fun `GIVEN a flow paging with state NotLoading true WHEN collect THEN do collect true`() =
        runTest {
            val users = buildPagingUserDataFlow(
                loadState = LoadState.NotLoading(endOfPaginationReached = true),
            )
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()

            verifyOnce { getPagingUsersUseCase() }
            state.itemSnapshotList.items.size shouldBeEqualTo 0
            state.loadState.refresh shouldBeInstanceOf LoadState.NotLoading::class
            state.loadState.refresh.endOfPaginationReached shouldBeEqualTo true
        }

    @Test
    fun `GIVEN a flow paging with state NotLoading true WHEN collect THEN do collect false`() =
        runTest {
            val users = buildPagingUserDataFlow(
                loadState = LoadState.NotLoading(endOfPaginationReached = false),
            )
            every { getPagingUsersUseCase() } returns users
            viewModel = UsersViewModel(getPagingUsersUseCase)

            val state = LazyPagingItemsTest(viewModel.userPagingFlow)
            state.initPagingDiffer()

            verifyOnce { getPagingUsersUseCase() }
            state.itemSnapshotList.items.size shouldBeEqualTo 0
            state.loadState.refresh shouldBeInstanceOf LoadState.NotLoading::class
            state.loadState.refresh.endOfPaginationReached shouldBeEqualTo false
        }

    @Test
    fun `GIVEN fun findUsers WHEN text is empty THEN flow doesn't filter`() = runTest {
        val text = ""
        val users = buildPagingUserDataFlow(
            listOf(
                User(id = 1, name = "test", email = "tst@t.com"),
                User(id = 2, name = "test2", email = "tst2@t.com"),
                User(id = 3, name = "rest3", email = "rst3@t.com"),
            ),
        )

        every { getPagingUsersUseCase() } returns users
        viewModel = UsersViewModel(getPagingUsersUseCase)

        var state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        var items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 3

        viewModel.findUsers(text)
        state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 3
        verify(exactly = 2) { getPagingUsersUseCase() }
    }

    @Test
    fun `GIVEN fun findUsers WHEN text 'test' THEN flow filter two name results`() = runTest {
        val text = "test"
        val users = buildPagingUserDataFlow(
            listOf(
                User(id = 1, name = "test", email = "tst@t.com"),
                User(id = 2, name = "test2", email = "tst2@t.com"),
                User(id = 3, name = "rest3", email = "rst3@t.com"),
            ),
        )

        every { getPagingUsersUseCase() } returns users
        viewModel = UsersViewModel(getPagingUsersUseCase)

        var state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        var items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 3

        viewModel.findUsers(text)
        state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 2
        verify(exactly = 2) { getPagingUsersUseCase() }
        items[0].name shouldBeEqualTo "test"
        items[1].name shouldBeEqualTo "test2"
    }

    @Test
    fun `GIVEN fun findUsers WHEN text 'test' THEN flow filter two email results`() = runTest {
        val text = "tst"
        val users = buildPagingUserDataFlow(
            listOf(
                User(id = 1, name = "test", email = "tst@t.com"),
                User(id = 2, name = "test2", email = "tst2@t.com"),
                User(id = 3, name = "rest3", email = "rst3@t.com"),
            ),
        )

        every { getPagingUsersUseCase() } returns users
        viewModel = UsersViewModel(getPagingUsersUseCase)

        var state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        var items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 3

        viewModel.findUsers(text)
        state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 2
        verify(exactly = 2) { getPagingUsersUseCase() }
        items[0].email shouldBeEqualTo "tst@t.com"
        items[1].email shouldBeEqualTo "tst2@t.com"
    }

    @Test
    fun `GIVEN fun findUsers WHEN text 'rest' THEN flow filter third user`() = runTest {
        val text = "rest"
        val users = buildPagingUserDataFlow(
            listOf(
                User(id = 1, name = "test", email = "tst@t.com"),
                User(id = 2, name = "test2", email = "tst2@t.com"),
                User(id = 3, name = "rest3", email = "rst3@t.com"),
            ),
        )

        every { getPagingUsersUseCase() } returns users
        viewModel = UsersViewModel(getPagingUsersUseCase)

        var state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        var items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 3

        viewModel.findUsers(text)
        state = LazyPagingItemsTest(viewModel.userPagingFlow)
        state.initPagingDiffer()
        items = state.itemSnapshotList.items

        items.size shouldBeEqualTo 1
        verify(exactly = 2) { getPagingUsersUseCase() }
        items[0].name shouldBeEqualTo "rest3"
    }

    private fun buildPagingUserDataFlow(
        users: List<User> = emptyList(),
        loadState: LoadState = LoadState.NotLoading(true),
    ): Flow<PagingData<User>> {
        val loadStates = LoadStates(loadState, loadState, loadState)
        val pagingData = PagingData.from(users, loadStates)
        return flowOf(pagingData)
    }
}
