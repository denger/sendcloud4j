package io.jstack.sendcloud4j.mail;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubstitutionTest {

    String varName = "name";
    String varAge = "age";

    @Test
    public void testSingleSub() {
        Substitution.Sub sub = Substitution.sub()
                .set(varName, "jack")
                .set(varAge, "19");

        assertEquals(sub.get(varName), "jack");
        assertEquals(sub.get(varAge), "19");
    }

    @Test
    public void testSubGroups() {
        Substitution.Sub subJack = Substitution.sub()
                .set(varName, "jack")
                .set(varAge, "18");
        Substitution.Sub subDenger = Substitution.sub()
                .set(varName, "denger")
                .set(varAge, "25");

        Substitution.SubGroup subGroups = Substitution.subGroup()
                .add(subJack)
                .add(subDenger);

        assertEquals(subGroups.toSubsMap()
                .get("%" + varName + "%")
                .get(0), subJack.get(varName));
        assertEquals(subGroups.toSubsMap()
                .get("%" + varName + "%")
                .get(1), subDenger.get(varName));
    }

}
