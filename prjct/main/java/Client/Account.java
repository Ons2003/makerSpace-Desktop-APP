package Client;


    public class Account {
        public enum UserRole {
            Student,
            Professor,
            SuperUser
        }

        public String first;
        public String last;
        public int dbID;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getSID() {
            return SID;
        }

        public void setSID(String SID) {
            this.SID = SID;
        }

        public String SID = "";
        public UserRole role;
        public String email;
        public String phoneNumber;
        public String password;

        public Account(String first, String universityID, UserRole role){
            this.first = first;
            SID = universityID;
            this.role = role;
        }

        public Account(){

        }

        public static UserRole DecodeRole(int role){
            switch(role){
                case 1:
                    return UserRole.SuperUser;
                case 2:
                    return UserRole.Student;
                case 3:
                    return UserRole.Professor;
            }
            return UserRole.SuperUser;
        }
    }

