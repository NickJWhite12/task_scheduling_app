package com.badger.taskschedulingapp.Main.services.postgresql


import com.badger.taskschedulingapp.Main.models.DataSource.Companion.transaction
import com.badger.taskschedulingapp.Main.models.Priority
import com.badger.taskschedulingapp.Main.services.PriorityService

class PriorityPostgresService: AbstractPostgresqlSerivce<Priority, Long>(), PriorityService{

    override fun findAll(): List<Priority> {
        return sessionFactory.transaction { session -> session.createQuery("from Priority").resultList } as List<Priority>
    }

    override fun findById(id: Long): Priority {
        return try{ sessionFactory.transaction { session ->
            session.get(Priority::class.java, id)
        } as Priority} catch(e: TypeCastException) {
            Priority()
        }
    }


    override fun save(obj: Priority): Priority {
        return findById(sessionFactory.transaction { session ->
            session.save(obj)
        } as Long)
    }

    override fun delete(obj: Priority) {
        sessionFactory.transaction {session -> session.delete(obj)}
    }

    override fun deleteById(id: Long) {
        delete(sessionFactory.transaction {session ->session.load(Priority::class.java, id)
        }as Priority)
    }

    override fun update(obj: Priority) {
        sessionFactory.transaction {session -> session.update(obj)}
    }
}