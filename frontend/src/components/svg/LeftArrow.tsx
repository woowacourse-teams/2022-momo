interface LeftArrowProps {
  width: number;
  color: string;
}

function LeftArrow({ width, color }: LeftArrowProps) {
  return (
    <svg
      width={width}
      height={width}
      viewBox="0 0 51 59"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g clipPath="url(#clip0_207_135)">
        <path
          d="M25.5557 9.46442L1.43499 26.8288C-0.224563 28.0232 -0.236854 29.9762 1.40753 31.1913L25.3078 48.8522C26.9522 50.0673 28.0813 49.233 27.8288 46.9885L26.9911 40.741C26.7386 38.4977 28.1166 36.6889 30.0679 36.7012L46.6004 36.8052C48.5517 36.8175 50.1458 34.9928 50.1601 32.7294L50.203 25.9068C50.2173 23.6423 48.6472 21.7976 46.695 21.7853L30.1634 21.6813C28.2122 21.669 26.857 19.8419 27.1377 17.6031L28.0541 11.3597C28.3337 9.11978 27.2153 8.2701 25.5557 9.46442Z"
          fill={color}
        />
      </g>
      <defs>
        <clipPath id="clip0_207_135">
          <rect
            width="58"
            height="50"
            fill="white"
            transform="translate(0 57.9988) rotate(-89.6394)"
          />
        </clipPath>
      </defs>
    </svg>
  );
}

export default LeftArrow;
