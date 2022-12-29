package ru.gb.lesson4;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.gb.lesson4.builder.MyClass;
import ru.gb.lesson4.builder.MyClassBuilder;

public class ExampleMyClassBuilderTest {

    @Test
    void createObject(){

        MyClass myClass1 = new MyClass();
        myClass1.setAttr1("");
        myClass1.setAttr2(true);
        myClass1.setAttr3(1l);

        MyClass myClass2 = new MyClass("",true,1l);

        MyClass myClass3 = new MyClassBuilder()
                .setAttr1("")
                .setAttr2(true)
                .setAttr3(1l)
                .createMyClass();

        Assertions.assertEquals(myClass1,myClass3);
        Assertions.assertFalse(myClass1==myClass3);
    }
}
