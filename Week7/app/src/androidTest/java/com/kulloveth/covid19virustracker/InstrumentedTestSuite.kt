package com.kulloveth.covid19virustracker

import com.kulloveth.covid19virustracker.data.RepositoryNewTest
import com.kulloveth.covid19virustracker.di.AllModulesTest
import com.kulloveth.covid19virustracker.di.RoomModuleTest
import com.kulloveth.covid19virustracker.ui.MainActivityTest
import com.kulloveth.covid19virustracker.ui.status.StatusFragmentTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


//runs all instrumented tests together
@RunWith(Suite::class)
@Suite.SuiteClasses(
    RepositoryNewTest::class,
    StatusFragmentTest::class,
    MainActivityTest::class,
    AllModulesTest::class,
    RoomModuleTest::class
)
class InstrumentedTestSuite