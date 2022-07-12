interface RightArrowProps {
  width: number;
  color: string;
}

function RightArrow({ width, color }: RightArrowProps) {
  return (
    <svg
      width={width}
      height={width}
      viewBox="0 0 50 58"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g clipPath="url(#clip0_207_163)">
        <path
          d="M24.7503 48.9388L48.7612 31.4229C50.4132 30.2182 50.4132 28.2651 48.7612 27.0603L24.7503 9.55018C23.0983 8.34544 21.9745 9.18692 22.241 11.4297L23.1181 17.6718C23.3847 19.9135 22.0181 21.7309 20.0668 21.7309L3.5339 21.7309C1.58262 21.7309 -5.62222e-06 23.5656 -5.72116e-06 25.8291L-6.01939e-06 32.6518C-6.11838e-06 34.9164 1.58163 36.7511 3.5339 36.7511L20.0658 36.7511C22.0171 36.7511 23.3837 38.5697 23.1171 40.8102L22.2401 47.0592C21.9745 49.3009 23.0983 50.1435 24.7503 48.9388Z"
          fill={color}
        />
      </g>
      <defs>
        <clipPath id="clip0_207_163">
          <rect
            width="58"
            height="50"
            fill="white"
            transform="translate(50 0.000488281) rotate(90)"
          />
        </clipPath>
      </defs>
    </svg>
  );
}

export default RightArrow;
