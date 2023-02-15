package tobyspring.vol1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tobyspring.vol1.user.domain.Level;
import tobyspring.vol1.user.domain.User;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void beforeEach(){
        user = new User();
    }

    @Test
    void upgradeLevel(){
        Level[] levels = Level.values();
        for (Level level : levels) {
            if(level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isSameAs(level.nextLevel());
        }
    }

    @Test
    void cannotUpgradeLevel(){
        Level[] levels = Level.values();
        for (Level level : levels) {
            if(level.nextLevel() != null) continue;
            assertThatThrownBy(()->{
                user.setLevel(level);
                user.upgradeLevel();
            }).isInstanceOf(IllegalStateException.class);
        }
    }

}
