package de.torpedomirror.backend.persistence.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MirrorUserModuleRepository : JpaRepository<MirrorUser, String> {

    @Modifying
    @Query(
        value = """
            INSERT INTO user_module (username, module_name) 
            VALUES (:username, :moduleName)
            ON CONFLICT (username, module_name) DO NOTHING
        """,
        nativeQuery = true
    )
    fun addModuleToUser(
        @Param("username") username: String,
        @Param("moduleName") moduleName: String
    )

    @Modifying
    @Query(
        value = "DELETE FROM user_module WHERE username = :username AND module_name = :moduleName",
        nativeQuery = true
    )
    fun removeModuleFromUser(
        @Param("username") username: String,
        @Param("moduleName") moduleName: String
    )
}
