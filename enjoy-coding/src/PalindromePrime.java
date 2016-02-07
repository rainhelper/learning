/**
 * Created by rainhelper on 2016. 2. 7..
 */
public class PalindromePrime {

    public int count(int L, int R) {

        if (!(L > 0 && L <= 1000 && R >= L && R <= 1000)) {
            return 0;
        }

        if (R == 1) {
            return 0;
        }

        if (L == 1) {
            L++;
        }

        int countOfResult = 0;

        for (int targetNumber = L; targetNumber <= R; targetNumber++) {

            String targetString = String.valueOf(targetNumber);

            if (targetString.length() == 2) {
                if (targetString.charAt(0) != targetString.charAt(1)) {
                    continue;
                }
            } else if (targetString.length() == 3) {
                if (targetString.charAt(0) != targetString.charAt(2)) {
                    continue;
                }
            }

            int countOfCandidate = 0;

            for (int i = 1; i <= targetNumber; i++) {
                if (targetNumber % i == 0) {
                    countOfCandidate++;
                }
            }

            if (countOfCandidate == 2) {
                countOfResult++;
            }
        }

        return countOfResult++;
    }
}
