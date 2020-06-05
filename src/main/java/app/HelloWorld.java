package app;

import monero.daemon.model.MoneroNetworkType;
import monero.wallet.MoneroWalletJni;
import monero.wallet.MoneroWalletRpc;
import monero.wallet.model.MoneroWalletConfig;

public class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello world!");
    MoneroWalletRpc walletRpc = new MoneroWalletRpc("http://localhost:38083", "rpc_user", "abc123");
    System.out.println("Height: " + walletRpc.getHeight());
    
    MoneroWalletJni walletJni = MoneroWalletJni.createWallet(new MoneroWalletConfig().setPath("./test_wallets/my_test_wallet").setPassword("abctesting123").setNetworkType(MoneroNetworkType.STAGENET));
    System.out.println(walletJni.getPrimaryAddress());
  }
}
