package com.example.iesbcoinapp.data.network.utils

import com.example.iesbcoinapp.core.utils.ResponseWrapper
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class RetrofitWrapperImplTest {

    private lateinit var retrofitWrapper: RetrofitWrapper

    @Before
    fun setUp() {
        retrofitWrapper = RetrofitWrapperImpl()
    }

    @Test
    fun `validate retrofitWrapperImpl return error when the request is error`() {

        val call: suspend () -> Response<Any> = {
            Response.error(404, mockk())
        }

        runBlockingTest {

            val result = retrofitWrapper.request {
                call.invoke()
            }

            assertThat(result).isInstanceOf(ResponseWrapper.Error::class.java)

        }
    }

    @Test
    fun `test retrofitWrapperImpl return success when the request is success`() {

        val call: suspend () -> Response<Any> = {
            Response.success(Any())
        }

        runBlockingTest {
            val result = retrofitWrapper.request {
                call()
            }

            assertThat(result).isInstanceOf(ResponseWrapper.Success::class.java)

        }
    }

}