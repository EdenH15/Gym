package gym.management.Sessions;

public abstract class SessionType {
        private final String name;
        private final int price;
        private final int maxPerson;

        protected SessionType(String name, int price, int max) {
            this.name = name;
            this.price = price;
            this.maxPerson = max;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getMaxPerson() {
            return maxPerson;
        }

        @Override
        public String toString() {
            return name;
        }

    public static final SessionType Pilates = new Pilates();
    public static final SessionType MachinePilates = new MachinePilates();
    public static final SessionType ThaiBoxing = new ThaiBoxing();
    public static final SessionType Ninja = new Ninja();
    }

