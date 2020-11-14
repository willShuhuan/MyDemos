package com.dsh.txlessons.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * @author DSH
 * @date 2020/11/11
 * @description
 */
public class OKIOMain {
    public static void main(String[] args) {
        try (BufferedSource source = Okio.buffer(Okio.source(new File("text.txt")))){
            System.out.println(source.readUtf8Line());//ab
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //不带buffer的
        //try (Source source = Okio.source(new File("text.txt"))){
        //    Buffer buffer = new Buffer();
        //    source.read(buffer,1024);
        //    System.out.println(buffer.readUtf8Line());//ab
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}
