package com.java.network.chapter02;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class Chapter02 {

    public static void main(String[] args) throws SocketException, UnknownHostException {
        usingTheNetworkInterfaceClass();
        gettingAMACAddress();
        creatingURIInstances();
        usingTheURLClass();
        obtainingInformationAboutAnIPAddress();
        isIPv4CompatibleAddressExample();
        //testingReachability();
        theInet4AddressClass();
    }

    private static void usingTheNetworkInterfaceClass() throws SocketException {
        Enumeration<NetworkInterface> interfaceEnum = NetworkInterface.getNetworkInterfaces();

        for (NetworkInterface networkInterface : Collections.list(interfaceEnum)) {
            System.out.println(networkInterface.getName() + ": " + networkInterface.getDisplayName()); //


            Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
            for (NetworkInterface anInterface : Collections.list(subInterfaces)) {
                System.out.println("sub: " + anInterface);
            }

            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(addresses)) {
                System.out.println("inetAddress = " + inetAddress); //IP가 보인다.
            }
        }
    }

    private static void gettingAMACAddress() throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("!IP address: " + address.getHostAddress());
        NetworkInterface network = NetworkInterface.getByInetAddress(address);
        System.out.println("network = " + network);
        System.out.println("MAC address: " + getMACIdentifier(network));

        Enumeration<NetworkInterface> interfaceEnum = NetworkInterface.getNetworkInterfaces();
        System.out.println("Name    MAC Address");
        for (NetworkInterface element : Collections.list(interfaceEnum)) {
            System.out.printf("%-6s  %s\n", element.getName(), getMACIdentifier(element));
        }
    }

    private static String getMACIdentifier(NetworkInterface networkInterface) {
        StringBuilder identifier = new StringBuilder();

        if(networkInterface == null) return "";

        try {
            byte[] macBuffer = networkInterface.getHardwareAddress();
            if (macBuffer != null) {
                for (int i = 0; i < macBuffer.length; i++) {
                    identifier.append(String.format("%02X%s", macBuffer[i], (i < macBuffer.length - 1) ? "-" : ""));
                }
            } else {
                return "---";
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        return identifier.toString();
    }

    private static void creatingURIInstances() {
        try {
            // Creating URI instances
            URI uri = new URI("https://www.packtpub.com/books/content/support");
            uri = new URI("https://en.wikipedia.org/wiki/"
                    + "URL_normalization#Normalization_process");
            uri = new URI("https", "en.wikipedia.org", "/wiki/URL_normalization", "Normalization_process");
            displayURI(uri);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    private static void displayURI(URI uri) {
        System.out.println("URI Information");
        System.out.println("getAuthority: " + uri.getAuthority());
        System.out.println("getScheme: " + uri.getScheme());
        System.out.println("getSchemeSpecificPart: " + uri.getSchemeSpecificPart());
        System.out.println("getHost: " + uri.getHost());
        System.out.println("getPath: " + uri.getPath());
        System.out.println("getQuery: " + uri.getQuery());
        System.out.println("getFragment: " + uri.getFragment());
        System.out.println("getUserInfo: " + uri.getUserInfo());
        System.out.println("normalize: " + uri.normalize());
    }

    private static void usingTheURLClass() {
        try {
            URL url;
            url = new URL("https://www.packtpub.com/books/content/support");
            url = new URL("http://www.packpub.com");
//            The following Generates: java.net.MalformedURLException: no protocol: www.packpub.com
//            url = new URL("www.packpub.com");
            url = new URL("http://pluto.jhuapl.edu/");
            url = new URL("http", "pluto.jhuapl.edu", 80, "News-Center/index.php");
            url = new URL("https://en.wikipedia.org/wiki/Uniform_resource_locator#Syntax");
            url = new URL("https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=url+syntax");
            System.out.println();
            displayURL(url);
            System.out.println("getContent: " + url.getContent());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void displayURL(URL url) {
        System.out.println("URL: " + url);
        System.out.printf("  Protocol: %-32s  Host: %-32s\n", url.getProtocol(), url.getHost());
        System.out.printf("      Port: %-32d  Path: %-32s\n", url.getPort(), url.getPath());
        System.out.printf(" Reference: %-32s  File: %-32s\n", url.getRef(), url.getFile());
        System.out.printf(" Authority: %-32s Query: %-32s\n", url.getAuthority(), url.getQuery());
        System.out.println(" User Info: " + url.getUserInfo());
    }

    private static void obtainingInformationAboutAnIPAddress() {
        try {
            InetAddress address = InetAddress.getByName("www.packtpub.com");
            displayInetAddressInformation(address);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static void displayInetAddressInformation(InetAddress address) {
        System.out.println("---InetAddress Information---");
        System.out.println(address);
        System.out.println("CanonicalHostName: " + address.getCanonicalHostName());
        System.out.println("HostName: " + address.getHostName());
        System.out.println("HostAddress: " + address.getHostAddress());
        System.out.println("---------");
    }

    private static void testingReachability() {
        try {
            String URLAddress = "www.packtpub.com";
            InetAddress[] addresses = InetAddress.getAllByName(URLAddress);
            for (InetAddress inetAddress : addresses) {
                try {
                    if (inetAddress.isReachable(10000)) {
                        System.out.println(inetAddress + " is reachable");
                    } else {
                        System.out.println(inetAddress + " is not reachable");
                    }
//                    Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 " + URLAddress);
//                    int returnVal = p1.waitFor();
//                    System.out.println(returnVal);
                } catch (IOException ex) {
                    ex.printStackTrace();
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
                }

            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static void isIPv4CompatibleAddressExample() {
        try {
            InetAddress names[] = InetAddress.getAllByName("www.google.com");
            for (InetAddress address : names) {
                if ((address instanceof Inet6Address)
                        && ((Inet6Address) address).isIPv4CompatibleAddress()) {
                    System.out.println(address
                            + " is IPv4 Compatible Address");
                } else {
                    System.out.println(address
                            + " is not a IPv4 Compatible Address");
                }
            }

            // Java 8 implementation
            System.out.println("--- Java 8 Implementation ---");
            names = InetAddress.getAllByName("www.google.com");
            Arrays.stream(names)
                    .map(address -> {
                        if ((address instanceof Inet6Address)
                                && ((Inet6Address) address).isIPv4CompatibleAddress()) {
                            return address + " is IPv4 Compatible Address";
                        } else {
                            return address + " is not IPv4 Compatible Address";
                        }
                    })
                    .forEach(result -> System.out.println(result));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static void theInet4AddressClass() {
        try {
            Inet4Address address;
            address = (Inet4Address) InetAddress.getByName("www.google.com");
            address = (Inet4Address) Inet4Address.getByName("www.google.com");

            // Special IPv4 Addresses
            address = (Inet4Address) Inet4Address.getByName("0.0.0.0");
            System.out.println(address.isAnyLocalAddress());
            address = (Inet4Address) Inet4Address.getByName("127.0.0.1");
            System.out.println(address.isLoopbackAddress());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
}
