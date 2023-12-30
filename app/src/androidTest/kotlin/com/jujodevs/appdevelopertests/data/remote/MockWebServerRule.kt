package com.jujodevs.appdevelopertests.data.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule :TestWatcher() {
    lateinit var server : MockWebServer

    override fun starting(description: Description?) {
        server = MockWebServer()
        server.start(8080)
        server.dispatcher = MockDispatcher()
    }

    override fun finished(description: Description?) {
        server.shutdown()
    }
}

private class MockDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.requestUrl?.query
        return when {
            path?.contains("page=1") == true ->
                MockResponse().fromJson("mock_users1.json")
            path?.contains("page=2") == true ->
                MockResponse().fromJson("mock_users2.json")
            path?.contains("page=") == true ->
                MockResponse().fromJson("mock_users3.json")
            else -> MockResponse().setResponseCode(404)
        }
    }
}
