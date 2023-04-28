package it.polito.wa2.server.ticketing.messages

import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message, Long?> {
}