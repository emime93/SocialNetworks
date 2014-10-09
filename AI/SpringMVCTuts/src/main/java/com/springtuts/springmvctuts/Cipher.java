/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtuts.springmvctuts;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Petricioiu
 */
public class Cipher {

    private byte[][] state;
    private byte[][] cipherKey;

    private byte[][] sBox;
    private byte[][] leGivenMatrix;
    private byte[][] rCon;
    private byte[][] leInput;

    public Cipher() {
        state = new byte[4][4];
        cipherKey = new byte[4][4];
        rCon = new byte[4][10];
    }

    public void setRCon() {
        rCon[0][0] = (byte) 1;
        rCon[0][1] = (byte) 2;
        rCon[0][2] = (byte) 4;
        rCon[0][3] = (byte) 8;
        rCon[0][4] = (byte) 16;
        rCon[0][5] = (byte) 32;
        rCon[0][6] = (byte) 64;
        rCon[0][7] = (byte) 128;
        rCon[0][8] = (byte) 27;
        rCon[0][9] = (byte) 54;

        for (int i = 1; i < 4; ++i) {
            for (int j = 0; j < 10; ++j) {
                rCon[i][0] = (byte) 0;
            }
        }
    }

    public void showRCon() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 10; ++j) {
                System.out.print(String.format("%02x", rCon[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public void setLeGivenMatrix(boolean inv) {

        leGivenMatrix = new byte[4][4];
        int j = 0;

        if (!inv) {

            for (int i = 0; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 2;
            }
            j = 0;
            for (int i = 1; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 1;
                leGivenMatrix[3 - i][3 - j + 1] = (byte) 3;
            }
            j = 0;
            for (int i = 2; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 1;
                leGivenMatrix[3 - i][3 - j + 1] = (byte) 1;
            }

            leGivenMatrix[0][3] = (byte) 1;
            leGivenMatrix[3][0] = (byte) 3;
        } else {
            for (int i = 0; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 14;
            }
            j = 0;
            for (int i = 1; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 9;
                leGivenMatrix[3 - i][3 - j + 1] = (byte) 11;
            }
            j = 0;
            for (int i = 2; i < 4; ++i) {
                leGivenMatrix[i][j++] = (byte) 13;
                leGivenMatrix[3 - i][3 - j + 1] = (byte) 13;
            }

            leGivenMatrix[0][3] = (byte) 9;
            leGivenMatrix[3][0] = (byte) 11;
        }
    }

    public byte[][] getsBox() {
        return sBox;
    }

    public void setSBox(byte[][] sBox) {
        this.sBox = sBox;
    }

    public void buildState(String plainText) {
        int i = 0;
        int j = 0;
        for (char c : plainText.toCharArray()) {
            state[i++][j] = (byte) c;
            if (i % 4 == 0) {
                i = 0;
                ++j;
            }
        }
    }

    public void subBytesTransform() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int x, y;
                x = Integer.parseInt(String.valueOf(String.format("%02x", state[i][j]).charAt(0)), 16);
                y = Integer.parseInt(String.valueOf(String.format("%02x", state[i][j]).charAt(1)), 16);
                state[i][j] = sBox[x][y];
            }
        }
    }

    public void shiftRowsTransform() {
        for (int i = 1; i < 4; ++i) {
            for (int k = 0; k < i; ++k) {
                byte aux = state[i][0];
                for (int j = 0; j < 3; ++j) {
                    state[i][j] = state[i][j + 1];
                }
                state[i][3] = aux;
            }
        }
    }

    public static byte FFMul(byte a, byte b) {
        byte aa = a, bb = b, r = 0, t;
        while (aa != 0) {
            if ((aa & 1) != 0) {
                r = (byte) (r ^ bb);
            }
            t = (byte) (bb & 0x80);
            bb = (byte) (bb << 1);
            if (t != 0) {
                bb = (byte) (bb ^ 0x1b);
            }
            aa = (byte) ((aa & 0xff) >> 1);
        }
        return r;
    }

    public void mixColumns() {
        byte[] aux = new byte[4];
        for (int j = 0; j < 4; ++j) {
            for (int i = 0; i < 4; ++i) {
                int s = 0;
                for (int k = 0; k < 4; ++k) {
                    s ^= (FFMul(leGivenMatrix[i][k], state[k][j]));
                }
                aux[i] = (byte) s;
            }
            for (int i = 0; i < 4; ++i) {
                state[i][j] = aux[i];
            }
        }
    }

    public void buildCipherKey(String password) {
        int i = 0;
        int j = 0;
        for (char c : password.toCharArray()) {
            cipherKey[i][j++] = (byte) c;
            if (j % 4 == 0) {
                j = 0;
                ++i;
            }
        }
    }

    public void showStateInHex() {
        System.out.println("\nState in hex: \n");
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                System.out.print(String.format("%02x", state[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public void showCipherKeyInHex() {
        System.out.println("Cipher key in hex: \n");

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                System.out.print(String.format("%02x", cipherKey[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public void showLeGivenMatrix() {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                System.out.print(String.format("%02x", leGivenMatrix[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public byte[][] getRoundKey(byte[][] key, int round) {

        //  System.out.println("Key #" + round);
        byte[][] returnKey = new byte[4][4];
        byte[][] auxKey = new byte[4][4];

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                auxKey[i][j] = key[i][j];
            }
        }

        byte aux = auxKey[0][3];

        for (int i = 0; i < 3; i++) {
            auxKey[i][3] = auxKey[i + 1][3];
        }

        auxKey[3][3] = aux;

        for (int j = 0; j < 4; ++j) {
            int x, y;

            x = Integer.parseInt(String.valueOf(String.format("%02x", auxKey[j][3]).charAt(0)), 16);
            y = Integer.parseInt(String.valueOf(String.format("%02x", auxKey[j][3]).charAt(1)), 16);

            auxKey[j][3] = sBox[x][y];
        }

        for (int i = 0; i < 4; ++i) {
            returnKey[i][0] = (byte) (auxKey[i][0] ^ auxKey[i][3] ^ rCon[i][round]);
        }

        for (int i = 0; i < 4; ++i) {
            for (int j = 1; j < 4; ++j) {
                returnKey[i][j] = (byte) (returnKey[i][j - 1] ^ key[i][j]);
            }
        }

        return returnKey;
    }

    public void setDefaultCipherKey() {
        cipherKey[0][0] = (byte) 43;
        cipherKey[0][1] = (byte) 40;
        cipherKey[0][2] = (byte) 171;
        cipherKey[0][3] = (byte) 9;
        cipherKey[1][0] = (byte) 126;
        cipherKey[1][1] = (byte) 174;
        cipherKey[1][2] = (byte) 247;
        cipherKey[1][3] = (byte) 207;
        cipherKey[2][0] = (byte) 21;
        cipherKey[2][1] = (byte) 210;
        cipherKey[2][2] = (byte) 21;
        cipherKey[2][3] = (byte) 79;
        cipherKey[3][0] = (byte) 22;
        cipherKey[3][1] = (byte) 166;
        cipherKey[3][2] = (byte) 136;
        cipherKey[3][3] = (byte) 60;
    }

    public byte[][] getCipherKey() {
        return cipherKey;
    }

    public void setDefaultState() {
        state[0][0] = (byte) 50;
        state[0][1] = (byte) 136;
        state[0][2] = (byte) 49;
        state[0][3] = (byte) 224;
        state[1][0] = (byte) 67;
        state[1][1] = (byte) 90;
        state[1][2] = (byte) 49;
        state[1][3] = (byte) 55;
        state[2][0] = (byte) 246;
        state[2][1] = (byte) 48;
        state[2][2] = (byte) 152;
        state[2][3] = (byte) 7;
        state[3][0] = (byte) 168;
        state[3][1] = (byte) 141;
        state[3][2] = (byte) 162;
        state[3][3] = (byte) 52;
    }

    public void addRoundKey(byte[][] roundKey) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                state[i][j] ^= roundKey[i][j];
            }
        }
    }

    byte[][][] roundKeys;

    public void encrypt() {
        buildRoundKeys();
        System.out.println("Encripting");

        byte[][] roundKey = new byte[4][4];
        leInput = new byte[4][4];

        addRoundKey(cipherKey);

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                leInput[i][j] = cipherKey[i][j];
            }
        }

        for (int k = 0; k < 9; ++k) {
            subBytesTransform();
            shiftRowsTransform();
            mixColumns();
            addRoundKey(roundKeys[k]);

            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    leInput[i][j] = roundKey[i][j];
                }
            }

        }

        subBytesTransform();
        shiftRowsTransform();
        addRoundKey(roundKeys[9]);

    }

    public void decrypt() {

        System.out.println("\nDecripting");

        addRoundKey(roundKeys[9]);
        invShiftRowsTransform();
        invSubBytesTransform();

        for (int k = 8; k >= 0; --k) {
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    leInput[i][j] = roundKeys[9][i][j];
                }
            }
            addRoundKey(roundKeys[k]);
            invMixColumns();
            invSubBytesTransform();
            invShiftRowsTransform();
        }

        addRoundKey(cipherKey);

    }

    public String decryptCipheredText(String cipherText, String password) throws UnsupportedEncodingException {
        cipherText = new String(Base64.decodeBase64(cipherText.getBytes()));    
        buildCipherKey(password);
        buildRoundKeys();
        buildState(cipherText);
        decrypt();
        return new String(Base64.decodeBase64(getCipherText().getBytes()));
    }

    public void buildRoundKeys() {

        roundKeys = new byte[10][4][4];
        leInput = new byte[4][4];

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                leInput[i][j] = cipherKey[i][j];
            }
        }

        for (int k = 0; k < 10; ++k) {
            roundKeys[k] = getRoundKey(leInput, k);
            for (int i = 0; i < 4; ++i) {
                for (int j = 0; j < 4; ++j) {
                    leInput[i][j] = roundKeys[k][i][j];
                }
            }
        }
    }

    public void invShiftRowsTransform() {
        for (int i = 1; i < 4; ++i) {
            for (int k = 0; k < i; ++k) {
                byte aux = state[i][3];
                for (int j = 3; j > 0; --j) {
                    state[i][j] = state[i][j - 1];
                }
                state[i][0] = aux;
            }
        }
    }

    public void invSubBytesTransform() {
        FileSystem fileSystem = FileSystem.getInstance();
        fileSystem.setFilePath("C:\\\\Users\\\\Petricioiu\\\\Documents\\\\develop\\\\facultate\\\\AI\\\\AESImplementation\\\\sBoxMatrixInv.txt");
        setSBox(fileSystem.readSBoxMatrix());
        subBytesTransform();
    }

    public void invMixColumns() {
        setLeGivenMatrix(true);
        mixColumns();
    }

    public String getCipherText() {
        StringBuffer sb = new StringBuffer();
        sb.append("");
        
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                sb.append((char) state[j][i]);
            }
        }
        String s;
        try {
            s = new String(org.apache.commons.codec.binary.Base64.encodeBase64(sb.toString().getBytes("UTF-8")));
            String decodedS = new String(org.apache.commons.codec.binary.Base64.decodeBase64(s.getBytes("UTF-8")));
            String c = "";
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(sb.toString().getBytes()));
    }
}
