package com.kulloveth.covid19virustracker

import com.kulloveth.covid19virustracker.di.NetworkModuleTest
import com.kulloveth.covid19virustracker.ui.news.NewsViewModelTest
import com.kulloveth.covid19virustracker.ui.status.StatusViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


//runs all local unit tests together
@RunWith(Suite::class)
@Suite.SuiteClasses(StatusViewModelTest::class, NewsViewModelTest::class,NetworkModuleTest::class)
class UnitTestSuite