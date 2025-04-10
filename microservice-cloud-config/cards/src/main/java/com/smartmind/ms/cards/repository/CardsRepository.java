package com.smartmind.ms.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmind.ms.cards.entity.Cards;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

    Optional<Cards> findByMobileNumber(String mobileNumber);

    Optional<Cards> findByCardNumber(String cardNumber);

}
