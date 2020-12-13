package home;

public class Steganography {

    private byte[] bytes;
    private String text;
    private String decryptedText;

    private int maxLen;

    public Steganography(){
        this.text = "";
        this.decryptedText = "";
        this.maxLen = 0;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public int getMaxLen() {
        return maxLen;
    }

    private String convertToBits(String text) {
        char[] chars = text.toCharArray();

        StringBuilder s = new StringBuilder();
        for(char x : chars) {
            s.append(String.format("%16s",Integer.toBinaryString(x)).replace(' ','0'));
        }
        return s.toString();
    }

    public void cipher(){ // First we should set text and bytes arr
        if(this.bytes.length != 0){
            int begin = 0;
            begin = begin | (this.bytes[10] & 0x000000FF) | ((this.bytes[11] & 0x0000FF00) << 8) | ((this.bytes[12] & 0x00FF0000) << 16) | ((this.bytes[13] & 0xFF000000) << 24);
            this.maxLen = ((this.bytes.length - begin) / 16) - 8;
            textCutter();
            String convertedText = convertToBits(this.text + "*****");
            char[] chars = convertedText.toCharArray();
            for(int i = 0, j = begin; i < chars.length;i++,j++) {
                if(chars[i] == '0') {
                    this.bytes[j] = (byte) (this.bytes[j] & 0b11111110);
                } else if (chars[i] == '1') {
                    this.bytes[j] = (byte) ((this.bytes[j] & 0b11111110) + 1);
                }
            }
        }
    }

    public void decipher(){
        if(this.bytes.length != 0){
            int begin = 0;
            begin = begin | (this.bytes[10] & 0x000000FF) | ((this.bytes[11] & 0x0000FF00) << 8) | ((this.bytes[12] & 0x00FF0000) << 16) | ((this.bytes[13] & 0xFF000000) << 24);
            this.maxLen = ((this.bytes.length - begin) / 16) - 8;
            StringBuilder out = new StringBuilder();
            char lastBit = 0b0000000000000001;
            for(int i = begin; i < (this.bytes.length-begin + 80);) {
                char c = 0x00;
                for(int j = 0; j < 16; j++,i++) {
                    c = (char)(c | ((((this.bytes[i] & lastBit) << 15) >>> j)));
                }
                out.append(c);
            }
            String[] tmpArr = out.toString().split("[*****]");
            String tmp = tmpArr[0];
            this.decryptedText = tmp;

            if(!out.toString().contains("*****")){
                this.decryptedText = "null";
            }else{
                this.decryptedText = tmp;
            }
        }
    }

    public void countMax(){
        int begin = 0;
        begin = begin | (this.bytes[10] & 0x000000FF) | ((this.bytes[11] & 0x0000FF00) << 8) | ((this.bytes[12] & 0x00FF0000) << 16) | ((this.bytes[13] & 0xFF000000) << 24);
        this.maxLen = ((this.bytes.length - begin) / 16) - 8;
    }

    private void textCutter(){
        if(this.text.length() >  this.maxLen){
            String sub = this.text.substring(0,this.maxLen);
            this.text = sub;
        }
    }
}
