package apps;

import monero.daemon.MoneroDaemonRpc;
import monero.wallet.MoneroWalletRpc;

public class HelloWorld {

  public static void main(String[] args) {
    System.out.println("Hello world!");
    MoneroWalletRpc walletRpc = new MoneroWalletRpc("http://localhost:38083", "rpc_user", "abc123");
    System.out.println("Height: " + walletRpc.getHeight());
    
    MoneroDaemonRpc daemonRpc = new MoneroDaemonRpc("http://localhost:38081", "superuser", "abctesting123");
    System.out.println(daemonRpc.getHeight());
  }
}
