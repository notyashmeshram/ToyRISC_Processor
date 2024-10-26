package conflict;

public class Conflict {
    public boolean isConflict(String B, String A){
        //B entered the pipeline first
        int opcode1 = Integer.parseInt(A.substring(0,5),2);
        int opcode2 = Integer.parseInt(B.substring(0,5),2);
        if(opcode1==24) {
            return false;
        }
        if((opcode2 >= 24 && opcode2<=29) || opcode2==23){
            return false;
        }
        int src1 = Integer.parseInt(A.substring(5,10),2);
        int src2=-1;
        if(opcode1<=20 && opcode1%2==0){
            src2 = Integer.parseInt(A.substring(10,15),2);
        }
        if(opcode1 == 23 || (opcode1>=25 && opcode1<=28)){
            src2 = Integer.parseInt(A.substring(10,15),2);
        }
        int dest = -1;
        if(opcode2==22 || (opcode2<24 && opcode2%2==1)){
            dest = Integer.parseInt(B.substring(10,15),2);
        } else if(opcode2<=20 && opcode2 %2==0){
            dest = Integer.parseInt(B.substring(15,20),2);
        }
        boolean hasSrc2 = true;
        if(opcode1 != 23){
            if(src2==-1){
                hasSrc2 = false;
            }
        }

        if(src1 == dest) return true;
        if(hasSrc2 && src2 == dest) return  true;
        return false;
    }
}
