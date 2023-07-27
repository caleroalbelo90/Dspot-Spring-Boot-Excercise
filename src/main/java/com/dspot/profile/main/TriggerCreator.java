package com.dspot.profile.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TriggerCreator {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TriggerCreator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTrigger() {
        String triggerSql = "CREATE OR REPLACE FUNCTION delete_friendship_entries() " +
                            "RETURNS TRIGGER AS $$ " +
                            "BEGIN " +
                            "    DELETE FROM Friendship " +
                            "    WHERE profile_id = OLD.id OR friend_id = OLD.id; " +
                            "    RETURN OLD; " +
                            "END; $$ LANGUAGE plpgsql; " +
                            "CREATE TRIGGER delete_friendship_trigger " +
                            "AFTER DELETE ON Profile " +
                            "FOR EACH ROW " +
                            "EXECUTE FUNCTION delete_friendship_entries();";

        jdbcTemplate.execute(triggerSql);
    }
}