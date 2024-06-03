package com.java.network.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Chapter01 {

    static class InetAddressExample {
        public static void main(String[] args) throws UnknownHostException {
            InetAddress address = InetAddress.getByName("www.google.com");
            System.out.println("address = " + address);//호스트 이름과 매칭되는 IP 주소도 볼 수 있다.

            System.out.println("address.getCanonicalHostName() = " + address.getCanonicalHostName());
            System.out.println("address.getHostAddress() = " + address.getHostAddress());
            System.out.println("address.getHostName() = " + address.getHostName());
        }
    }

    static class URLConnectionExample {
        public static void main(String[] args) throws IOException {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection(); //URLConnection 인스턴스 생성
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();

            /**
             * <!doctype html><html itemscope="" itemtype="http://schema.org/WebPage" lang="ko"><head><meta content="text/html; charset=UTF-8" http-equiv="Content-Type"><meta content="/images/branding/googleg/1x/googleg_standard_color_128dp.png" itemprop="image"><title>Google</title><script nonce="QkoYqYrtHgZLbVgrbNJiAw">(function(){var _g={kEI:'r7ddZoTtM7XN1e8P58zoyQY',kEXPI:'0,793344,2906970,635,361,45,31,448527,90132,2872,2891,11754,61296,16105,230,107242,6636,49751,2,39761,6699,41946,57737,2,2,1,26632,8155,8860,14490,22436,52238,20199,73178,2266,764,15816,1804,35268,11814,1635,13492,5254652,891,621,40,5991769,2839987,27982148,1008,15665,43886,3,318,4,1281,3,2124363,23029351,4117,7440,1242,8409,6577,10087,18675,9352,14643,22227,1922,8589,2370,6407,2964,10881,10475,4690,7981,201,390,5545,11940,3463,11916,16175,10085,11583,3011,3745,155,399,2085,4400,5225,3878,7737,7,5265,3,1324,434,992,1113,745,2,2,219,540,3092,206,122,2648,569,4,123,2881,705,2244,434,895,1,266,2,3053,5,572,3384,4031,1142,3,1572,224,678,871,519,890,3518,3,1309,1423,1740,148,44,3851,96,1297,2292,2335,447,993,1119,3,53,24,1177,52,442,177,425,34,518,2113,682,1539,652,519,309,41,3,1417,3,5,4429,2,2,576,36,4,232,27,302,993,1404,329,1604,811,378,25,7,20,503,51,136,729,161,75,374,18,66,3331,1047,883,843,5,827,696,3,1799,1,1088,148,106,111,169,272,196,456,8,467,1170,49,1325,552,765,155,1128,236,372,376,215,44,412,300,291,3,45,24,471,163,227,996,263,190,4,330,468,89,643,274,217,163,527,132,2,406,179,48,90,108,158,221,267,141,134,2323,394,105,244,466,486,70,1128,557,33,169,262,44,327,349,127,6,182,68,535,3,545,73,21215063,365066,4264,3,5247,3047,5875,1993,1421,609,3392678',kBL:'Cfkd',kOPI:89978449};(function(){var a;(null==(a=window.google)?0:a.stvsc)?google.kEI=_g.kEI:window.google=_g;}).call(this);})();(function(){google.sn='webhp';google.kHL='ko';})();(function(){
             * var h=this||self;function l(){return void 0!==window.google&&void 0!==window.google.kOPI&&0!==window.google.kOPI?window.google.kOPI:null};var m,n=[];function p(a){for(var b;a&&(!a.getAttribute||!(b=a.getAttribute("eid")));)a=a.parentNode;return b||m}function q(a){for(var b=null;a&&(!a.getAttribute||!(b=a.getAttribute("leid")));)a=a.parentNode;return b}function r(a){/^http:/i.test(a)&&"https:"===window.location.protocol&&(google.ml&&google.ml(Error("a"),!1,{src:a,glmm:1}),a="");return a}
             * .. 이렇게 콘솔에 출력된다.
             */
        }
    }

    static class URLConnectionV2Example {
        public static void main(String[] args) throws IOException {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            ReadableByteChannel channel = Channels.newChannel(inputStream);

            ByteBuffer buffer = ByteBuffer.allocate(64);
            String line = null;

            while(channel.read(buffer) > 0) {
                System.out.println(new String(buffer.array()));
                buffer.clear();
            }
            channel.close();
        }
    }
}
