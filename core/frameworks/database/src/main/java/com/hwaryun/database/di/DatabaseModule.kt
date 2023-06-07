package com.hwaryun.database.di

//@Module
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//    @Provides
//    fun provideFavoriteDao(database: AppDatabase): BaseDao<Unit> {
//        return database.yourDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRoomDatabase(context: Context): AppDatabase {
//        return Room.databaseBuilder(context, AppDatabase::class.java, "databasename")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//}