package computer.ethernet.onset.di

import computer.ethernet.onset.data.substances.parse.SubstanceParser
import computer.ethernet.onset.data.substances.parse.SubstanceParserInterface
import computer.ethernet.onset.data.substances.repositories.SearchRepository
import computer.ethernet.onset.data.substances.repositories.SearchRepositoryInterface
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.data.substances.repositories.SubstanceRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSubstanceParser(
        substanceParser: SubstanceParser
    ): SubstanceParserInterface

    @Binds
    @Singleton
    abstract fun bindSubstanceRepository(
        substanceRepository: SubstanceRepository
    ): SubstanceRepositoryInterface

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        substanceRepository: SearchRepository
    ): SearchRepositoryInterface
}