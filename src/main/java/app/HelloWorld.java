package app;

import monero.wallet.MoneroWalletRpc;

public class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello world!");
    MoneroWalletRpc walletRpc = new MoneroWalletRpc("http://localhost:38083", "rpc_user", "abc123");
    System.out.println("Height: " + walletRpc.getHeight());
  }
}
