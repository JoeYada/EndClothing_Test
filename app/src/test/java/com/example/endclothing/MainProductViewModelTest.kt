package com.example.endclothing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.example.endclothing.network.repo.ProductRepository
import com.example.endclothing.utils.LoadState
import com.example.endclothing.views.MainProductViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainProductViewModelTest {
    @Mock
    private lateinit var mRepo: ProductRepository

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var productViewModel: MainProductViewModel


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test refresh data from repository do what it should do`() = runBlocking {

        productViewModel = MainProductViewModel(mRepo)
        val liveDataTestUtil = LiveDataTestUtil<LoadState>()

        //action
        //when ProductViewModel is created getProducts is called.
        //verify
        Mockito.verify(mRepo).getProducts()
        val dataEmitted = liveDataTestUtil.getValue(productViewModel.loadState)
        assertEquals(LoadState.Status.SUCCESS.name,dataEmitted?.status?.name)
    }

    @Test
    fun `test loading error is called`() = runBlocking {
        //prep
        //action
        //when productViewModel is created getProducts is called.
        //verify
        Mockito.`when`(mRepo.getProducts()).thenThrow(RuntimeException())
        productViewModel = MainProductViewModel(mRepo)
        val mediatorLiveData = MediatorLiveData<LoadState>()
        mediatorLiveData.addSource(productViewModel.loadState) {
            when(it.status) {
                LoadState.Status.LOADING -> {
                    //ignore
                }
                else -> {
                    assertEquals(LoadState.Status.FAILED.name,it?.status?.name)
                }
            }
        }
    }
}