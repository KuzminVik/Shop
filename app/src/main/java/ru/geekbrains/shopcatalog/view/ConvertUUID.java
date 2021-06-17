package ru.geekbrains.shopcatalog.view;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class ConvertUUID {

    @NotNull
    public static String con (@NotNull String str){
        byte[] encodedAsBytes = str.getBytes(StandardCharsets.UTF_8);
        return Base64.getMimeEncoder().encodeToString(encodedAsBytes);
    }
}
