public class CheckArr {

    public int[] after4 (int[] arr) {
        int lastFour = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4){
                lastFour = i;
            }
        }
        if (lastFour == -1) throw new RuntimeException();

        int[] resultArr = new int[arr.length - lastFour - 1];
        for (int i = 0; i < resultArr.length; i++) {
            resultArr[i] = arr[i + lastFour + 1];
        }

        return resultArr;
    }

    public boolean isOneOrFour (int[] arr){
        int countFour = 0;
        int countOne = 0;
        int countOther = 0;
        for (int n : arr){
            if(n == 4) countFour++;
            else if (n == 1) countOne++;
            else countOther++;
        }
        if ( countFour == 0  || countOne == 0 || countOther > 0) return false;
        else return true;
    }

}
