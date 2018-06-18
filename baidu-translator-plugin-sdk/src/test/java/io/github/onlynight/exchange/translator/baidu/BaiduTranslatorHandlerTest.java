package io.github.onlynight.exchange.translator.baidu;

import org.junit.Test;

import java.util.Arrays;

public class BaiduTranslatorHandlerTest {

    @Test
    public void testLoadLanguage() {
        BaiduTranslatorHandler handler = new BaiduTranslatorHandler();
        System.out.println(Arrays.toString(handler.getSupportLanguage().toArray()));
    }

}