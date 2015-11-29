package it.fff.client.util;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

import it.fff.clientserver.common.secure.DHSecureConfiguration;

public class DHUtils {

	public String generateClientPublicKey(KeyAgreement clientKeyAgree){
		String clientPpublicKey = "";
		
			try{
				byte[] clientPubKeyEnc = null;
			
				AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
				paramGen.init(512);
				AlgorithmParameters params = paramGen.generateParameters();
				DHParameterSpec dhSkipParamSpec = (DHParameterSpec)params.getParameterSpec(DHParameterSpec.class);
			    		
//		        Il client genera la sua coppia di chiavi Diffie-Hellman usando i parametri generati sopra
		        System.out.println("CLIENT: Generate DH keypair ...");
		        KeyPairGenerator clientKpairGen = KeyPairGenerator.getInstance("DH");
		        clientKpairGen.initialize(dhSkipParamSpec);
		        KeyPair clientKpair = clientKpairGen.generateKeyPair();
		
		        // Il client inizializza il proprio oggetto KeyAgreement
		        System.out.println("CLIENT: Initialization ...");
		        clientKeyAgree.init(clientKpair.getPrivate());
		
		        // Il coient codifica la propria chiave pubblica e la converte in base64 per inviarla al server
		        clientPubKeyEnc = clientKpair.getPublic().getEncoded();
		        clientPpublicKey = Base64.encodeBase64String(clientPubKeyEnc);

			} catch(Exception e){
				e.printStackTrace();
			}
		
		return clientPpublicKey;
	}
	
	public String generateSharedSecret(KeyAgreement clientKeyAgree, byte[] serverPublicKey){
		String sharedSecret = "";
			try{
		        /*
		         * Il client usa la chiave pubblica del server per la sua fase del protocollo
		         * Prima deve istanziare la sua chiave pubblica da quella pubblica del server
		         */
		        KeyFactory clientKeyFac = KeyFactory.getInstance("DH");
		        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(serverPublicKey);
		        PublicKey serverPubKey = clientKeyFac.generatePublic(x509KeySpec);
		        System.out.println("CLIENT: Execute PHASE1 ...");
		        clientKeyAgree.doPhase(serverPubKey, true);			
		        
		        byte[] clientSharedSecret = clientKeyAgree.generateSecret();
		        int clientLen = clientSharedSecret.length;
		        sharedSecret = toHexString(clientSharedSecret);
		        System.out.println(sharedSecret);
			} catch(Exception e){
				e.printStackTrace();
			}

			return sharedSecret;
	}
	
    private String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();

        int len = block.length;

        for (int i = 0; i < len; i++) {
             byte2hex(block[i], buf);
             if (i < len-1) {
                 buf.append(":");
             }
        }
        return buf.toString();
    }
    
    private void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                            '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    } 	
}
