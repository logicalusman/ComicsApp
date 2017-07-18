package com.le.comicsapp;

import com.le.comicsapp.util.Utils;

import junit.framework.Assert;

import org.junit.Test;

public class UtilsTest {
    @Test
    public void md5Hash1_isCorrect() throws Exception {
        String ts = "12345322";
        String priKey = "jskdsdkfsdkfhfsdf";
        String pubKey = "kjj434lkljljj43";
        String msg = String.format("%s%s%s", ts, priKey, pubKey);
        Assert.assertEquals("149736e30fa4f103a550a9904de3528a", Utils.md5HashOf(msg));
    }

    @Test
    public void md5Hash2_isCorrect() throws Exception {
        String ts = "12334422";
        String priKey = "2222";
        String pubKey = "12";
        String msg = String.format("%s%s%s", ts, priKey, pubKey);
        Assert.assertEquals("a5a472c746d9f7853ab544d016913cbc", Utils.md5HashOf(msg));
    }

}