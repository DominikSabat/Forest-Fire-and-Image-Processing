public enum TreeState{
    ALIVE {
        @Override
        public String toString() {
            return "alive";
        }
    },
    ONFIRE{
        @Override
        public String toString() {
            return "onfire";
        }
    },
    DEAD{
        @Override
        public String toString() {
            return "dead";
        }
    }

}