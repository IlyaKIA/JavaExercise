public class Tests {

    @BeforeSuite
    void before1 () {
        System.out.println("BeforeSuite");
    }

    @Test(priority = 1)
    void test () {
        System.out.println("Test №1");
    }

    @Test(priority = 100)
    void test100 () {
        System.out.println("Test №100");
    }

    @Test(priority = -100)
    void test_100 () {
        System.out.println("Test №-100");
    }

    @Test (priority = 5)
    void test5 () {
        System.out.println("Test №5");
    }

    @Test (priority = 5)
    void testSecond5 () {
        System.out.println("Test №Second5");
    }

    @Test (priority = 3)
    void test3 () {
        System.out.println("Test №3");
    }

    @AfterSuite
    void after () {
        System.out.println("AfterSuite");
    }
}
