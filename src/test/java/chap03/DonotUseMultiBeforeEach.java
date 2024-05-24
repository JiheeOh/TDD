package test.java.chap03;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DonotUseMultiBeforeEach {

   @BeforeEach
   void before3(){
       System.out.println("before3");
       System.out.println("beforeB");
   }

    @Test
    void d(){
        System.out.println("d");

    }

    @AfterEach
    void afterC(){
        System.out.println("afterC");
        System.out.println("after5");
    }


}
