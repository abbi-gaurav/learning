package coursera.alg1.lect2;

public enum OPERATOR {
	PLUS {
		@Override
		public Double compute(Double val1, Double val2) {
			return val1 + val2;
		}
	},
	MINUS {
		@Override
		public Double compute(Double val1, Double val2) {
			return val1 - val2;
		}
	},
	MULTIPLY {
		@Override
		public Double compute(Double val1, Double val2) {
			return val1 * val2;
		}
	},
	DIVIDE {
		@Override
		public Double compute(Double val1, Double val2) {
			return val1 / val2;
		}
	};

	public abstract Double compute(Double val1, Double val2);
}