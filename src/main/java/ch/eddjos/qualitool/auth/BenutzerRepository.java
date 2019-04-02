package ch.eddjos.qualitool.auth;

import org.springframework.data.jpa.repository.JpaRepository;

interface BenutzerRepository extends JpaRepository<Benutzer,Integer> {
    public Benutzer findBenutzerByTokenEquals(String token);
    public Benutzer findBenutzerByNicknameEquals(String username);
}
