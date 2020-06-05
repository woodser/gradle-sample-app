package apps;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import monero.daemon.MoneroDaemonRpc;
import monero.daemon.model.MoneroNetworkType;
import monero.wallet.MoneroWallet;
import monero.wallet.MoneroWalletJni;
import monero.wallet.model.MoneroOutputWallet;
import monero.wallet.model.MoneroWalletConfig;
import monero.wallet.model.MoneroWalletListener;

public class IncomingTransferNotifier {
  
  private static MoneroDaemonRpc daemon = new MoneroDaemonRpc("http://localhost:38081", "superuser", "abctesting123");
  
  public static void main(String[] args) throws InterruptedException {
    
    // listen to 3 view-only wallets
    IncomingTransferNotifier server = new IncomingTransferNotifier();
    for (int i = 0; i < 3; i++) {

      // create wallet with random seed that will be listened to
      MoneroWallet wallet = MoneroWalletJni.createWallet(new MoneroWalletConfig()
              .setNetworkType(MoneroNetworkType.STAGENET)
              .setPassword("supersecretpassword"));
      
      // server listens to view-only wallet
      server.addViewOnlyWallet(wallet.getPrimaryAddress(), wallet.getPrivateViewKey(), daemon.getHeight());
      
      // close wallet
      wallet.close();
    }
    
    // loop indefinitely as wallets listen for transfers
    while (true) TimeUnit.SECONDS.sleep(10);
  }
  
  public void addViewOnlyWallet(String primaryAddress, String viewKey, long restoreHeight) {
    
    // create view-only wallet
    MoneroWalletJni wallet = MoneroWalletJni.createWallet(new MoneroWalletConfig()
            .setNetworkType(MoneroNetworkType.STAGENET)
            .setPath("test_wallets/" + UUID.randomUUID().toString())
            .setPassword("supersecretpassword")
            .setPrimaryAddress(primaryAddress)
            .setPrivateViewKey(viewKey)
            .setServer(daemon.getRpcConnection())
            .setRestoreHeight(restoreHeight));
    
    // sync wallet
    wallet.sync();
    
    // listen for incoming transfers
    wallet.addListener(new MoneroWalletListener() {
      @Override
      public void onOutputReceived(MoneroOutputWallet output) {
        System.out.println("Wallet with view key " + wallet.getPrivateViewKey() + " received funds!");
      }
    });
    
    // sync in background
    wallet.startSyncing();
    System.out.println("Listening for incoming transfers to " + primaryAddress + " with view key " + viewKey);
  }
}
