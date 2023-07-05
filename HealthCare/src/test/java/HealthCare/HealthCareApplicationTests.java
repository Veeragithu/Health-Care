package HealthCare;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.cerner.HealthCare.HealthCareApplication;
@SpringBootApplication
//@SpringBootTest
class HealthCareApplicationTests {

    @Test
    void contextLoads() {
        HealthCareApplication.main(new String[]{});
       
    }

}
