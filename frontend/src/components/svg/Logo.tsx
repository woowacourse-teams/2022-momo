interface LogoProps {
  color?: string;
  width?: number;
}

function Logo({ color = '#FFFFFF', width = 100 }: LogoProps) {
  return (
    <svg
      version="1.0"
      xmlns="http://www.w3.org/2000/svg"
      width={width}
      height={width / 2}
      viewBox="0 0 1920.000000 841.000000"
      preserveAspectRatio="xMidYMid meet"
    >
      <g
        transform="translate(0.000000,841.000000) scale(0.100000,-0.100000)"
        fill={color}
        stroke="none"
      >
        <path
          d="M4560 8395 c-179 -33 -625 -182 -800 -269 -174 -86 -421 -272 -538
  -405 -111 -126 -223 -270 -295 -377 -144 -219 -173 -377 -164 -888 4 -224 9
  -321 25 -421 40 -250 89 -425 154 -545 54 -98 169 -271 228 -340 26 -30 76
  -98 112 -150 75 -107 124 -157 218 -220 82 -55 152 -65 218 -31 98 50 79 141
  -42 203 -87 44 -187 142 -306 299 -193 256 -286 500 -345 904 -18 126 -21 486
  -5 658 17 180 69 333 151 449 75 104 237 306 285 354 94 94 279 233 430 322
  119 71 360 167 545 218 150 41 187 48 269 48 110 1 160 -17 229 -79 66 -60
  153 -183 213 -301 70 -139 84 -177 152 -409 75 -257 82 -287 111 -460 14 -82
  46 -240 71 -350 84 -368 114 -529 129 -680 34 -355 30 -445 -24 -601 -49 -139
  -116 -221 -333 -401 -107 -89 -125 -114 -109 -149 18 -40 75 -59 139 -46 81
  17 191 75 302 158 154 116 190 166 265 372 l38 104 -6 236 c-8 311 -20 431
  -68 641 -22 96 -78 361 -124 590 -84 417 -115 537 -199 774 -74 209 -168 392
  -271 527 -60 78 -182 190 -242 223 -101 54 -257 70 -413 42z"
        />
        <path
          d="M8465 7979 c-70 -18 -329 -117 -660 -250 -187 -76 -440 -241 -545
  -356 -111 -121 -149 -200 -220 -458 -69 -252 -116 -743 -91 -960 24 -211 113
  -527 193 -686 114 -229 367 -479 603 -597 86 -43 199 -76 233 -67 31 7 82 52
  82 72 0 52 -79 120 -178 155 -116 41 -194 90 -296 187 -74 71 -182 216 -240
  322 -96 175 -154 421 -176 741 -9 130 -7 162 16 343 72 568 161 786 390 958
  122 92 233 152 344 188 167 53 262 90 405 158 l140 66 95 -1 c64 0 124 -8 185
  -23 50 -12 133 -26 186 -31 161 -15 271 -73 410 -216 90 -93 127 -161 169
  -309 35 -125 55 -261 69 -475 6 -85 15 -218 21 -295 5 -77 18 -225 29 -330 11
  -104 32 -367 47 -583 30 -447 30 -449 -45 -607 -50 -104 -96 -155 -176 -196
  -80 -40 -110 -69 -102 -101 11 -41 63 -63 147 -63 91 1 149 25 216 94 58 58
  168 277 189 377 36 166 28 563 -20 959 -14 110 -34 317 -45 460 -41 534 -51
  632 -71 714 -30 122 -94 288 -135 349 -50 76 -289 307 -344 333 -65 31 -205
  64 -370 88 -80 12 -176 29 -215 37 -112 25 -154 26 -240 3z"
        />
        <path
          d="M12775 7609 c-165 -8 -276 -32 -354 -74 -110 -60 -326 -278 -455
  -458 -55 -77 -225 -409 -250 -489 -45 -145 -58 -238 -63 -468 -6 -274 4 -381
  57 -610 53 -229 74 -301 126 -440 23 -64 59 -180 79 -259 83 -330 110 -381
  200 -381 109 0 173 138 111 244 -13 23 -45 115 -71 206 -26 91 -73 244 -106
  340 -32 96 -68 214 -79 261 -48 202 -67 455 -51 684 9 130 16 171 56 310 25
  88 57 182 70 209 14 28 25 62 25 76 0 40 165 277 281 404 57 63 152 140 219
  179 63 36 201 57 380 57 170 0 261 -10 460 -51 176 -36 250 -68 331 -146 97
  -92 231 -287 322 -469 77 -155 111 -264 196 -639 136 -603 176 -894 152 -1106
  -17 -151 -47 -285 -80 -355 -35 -74 -111 -160 -185 -206 -28 -17 -63 -42 -78
  -54 -15 -12 -71 -39 -125 -60 -154 -59 -163 -69 -117 -124 55 -65 144 -90 236
  -66 78 21 323 182 425 279 72 69 80 81 111 163 92 253 111 505 57 799 -8 44
  -23 145 -35 225 -19 138 -56 330 -115 595 -59 268 -85 360 -130 458 -118 256
  -197 406 -262 492 -75 100 -244 275 -312 324 -66 46 -202 85 -440 125 -228 39
  -282 41 -586 25z"
        />
        <path
          d="M16858 7365 c-3 -3 -54 -10 -114 -15 -189 -15 -366 -82 -538 -201
  -43 -30 -104 -69 -135 -87 -70 -42 -136 -92 -277 -208 -185 -154 -300 -291
  -422 -504 -74 -129 -142 -279 -142 -313 0 -13 -19 -96 -41 -183 -41 -157 -41
  -162 -49 -394 -9 -277 0 -420 35 -550 35 -128 61 -183 160 -330 101 -151 317
  -377 435 -456 109 -72 316 -173 382 -186 49 -9 61 -8 94 8 21 11 45 34 55 53
  18 33 18 35 -1 61 -29 40 -101 83 -220 130 -167 66 -189 81 -307 213 -208 232
  -252 296 -305 439 -16 46 -38 106 -48 134 -31 83 -40 169 -40 378 0 219 13
  338 60 532 64 268 198 520 372 700 110 114 222 201 473 366 249 165 339 200
  542 214 l88 6 60 -44 c71 -53 120 -108 242 -278 191 -265 270 -410 342 -625
  57 -172 79 -297 96 -538 8 -116 22 -306 31 -422 11 -147 15 -287 12 -470 -4
  -251 -6 -263 -32 -338 -14 -44 -53 -125 -85 -180 -113 -196 -115 -206 -64
  -247 30 -23 80 -26 127 -6 47 19 116 84 135 126 9 19 33 64 53 100 44 79 83
  199 97 304 16 111 13 563 -4 826 -42 632 -87 852 -238 1160 -136 277 -429 655
  -590 761 -68 44 -218 85 -239 64z"
        />
        <path
          d="M4643 7223 c-33 -6 -69 -55 -77 -103 -7 -46 7 -140 29 -194 30 -71
  120 -115 170 -82 45 30 60 72 60 171 -1 99 -22 157 -68 188 -27 18 -81 27
  -114 20z"
        />
        <path
          d="M4320 7137 c-76 -38 -81 -236 -8 -289 97 -69 218 19 218 158 0 50 -3
  59 -33 89 -49 49 -127 67 -177 42z"
        />
        <path
          d="M7518 6983 c-49 -8 -129 -160 -130 -247 0 -45 4 -54 36 -83 40 -37
  87 -43 136 -18 85 44 136 204 95 300 -20 49 -54 61 -137 48z"
        />
        <path
          d="M7815 6905 c-19 -20 -45 -55 -56 -78 -12 -23 -34 -57 -48 -76 -34
  -45 -37 -127 -6 -161 27 -30 76 -50 122 -50 35 0 41 4 72 51 61 94 85 157 85
  232 1 63 -1 71 -27 93 -44 37 -97 33 -142 -11z"
        />
        <path
          d="M13344 6594 c-40 -61 -6 -184 74 -267 48 -50 82 -67 132 -67 52 0 95
  39 106 95 15 79 -35 198 -102 241 -17 12 -54 19 -109 22 -82 4 -83 4 -101 -24z"
        />
        <path
          d="M13048 6504 c-38 -20 -43 -51 -23 -131 21 -84 46 -126 98 -164 46
  -33 108 -39 144 -14 21 15 63 128 63 168 0 22 -85 118 -124 140 -38 21 -120
  22 -158 1z"
        />
        <path
          d="M15753 6090 c-49 -30 -66 -72 -71 -178 -6 -135 8 -153 129 -167 46
  -5 59 -3 81 15 15 11 32 37 39 57 16 49 6 164 -21 236 l-21 57 -52 0 c-34 0
  -63 -7 -84 -20z"
        />
        <path
          d="M16153 6085 c-53 -37 -69 -177 -29 -254 19 -35 70 -71 102 -71 35 0
  88 42 112 90 46 91 21 189 -58 231 -42 23 -98 24 -127 4z"
        />
        <path
          d="M4905 6015 c-27 -8 -104 -25 -170 -39 -66 -13 -145 -34 -176 -45
  -133 -50 -273 -160 -309 -243 -44 -103 -8 -237 98 -365 92 -110 232 -156 362
  -118 30 9 77 21 105 26 60 13 94 30 139 69 48 43 63 83 42 115 -42 63 -127 73
  -265 31 -47 -14 -97 -26 -113 -26 -75 0 -124 65 -116 152 6 61 39 98 123 138
  79 37 303 100 357 100 78 1 158 53 173 112 6 22 1 33 -22 57 -50 50 -132 62
  -228 36z"
        />
        <path
          d="M9010 5943 c-25 -2 -94 -10 -155 -18 -325 -44 -743 -64 -866 -41
  -117 21 -238 -88 -169 -152 42 -39 91 -44 409 -44 271 -1 410 7 560 28 l44 7
  -7 -29 c-14 -57 -61 -121 -163 -220 -74 -72 -115 -104 -143 -113 -67 -20 -89
  -77 -51 -135 24 -36 57 -55 95 -56 66 0 253 143 353 271 109 140 208 278 224
  314 36 78 15 185 -35 185 -14 0 -30 1 -38 3 -7 2 -33 2 -58 0z"
        />
        <path
          d="M12348 5495 c-37 -21 -44 -57 -25 -119 9 -28 32 -111 52 -184 68
  -260 119 -323 308 -387 140 -47 268 -30 330 44 26 31 26 34 11 63 -20 38 -77
  63 -177 77 -90 13 -133 29 -164 62 -27 29 -50 92 -59 157 -10 74 -3 76 171 52
  82 -12 165 -18 186 -15 101 17 115 123 26 189 -26 19 -42 21 -200 21 -161 1
  -175 2 -217 24 -58 31 -200 40 -242 16z"
        />
        <path
          d="M16280 5044 c-104 -35 -106 -35 -235 -30 -71 4 -160 9 -196 12 -106
  10 -155 -24 -162 -111 -2 -28 1 -31 63 -52 36 -12 100 -26 143 -32 103 -14
  382 -14 449 0 109 23 188 84 188 145 0 45 -21 82 -52 93 -49 17 -86 13 -198
  -25z"
        />
        <path
          d="M213 4604 c-50 -25 -102 -89 -140 -171 -56 -122 -64 -176 -68 -483
  -4 -297 -2 -312 51 -341 13 -6 48 -40 78 -74 109 -123 208 -167 357 -158 76 5
  87 3 112 -17 15 -12 27 -25 27 -30 0 -5 20 -37 44 -72 24 -35 76 -119 116
  -188 152 -259 239 -370 406 -513 127 -109 155 -140 190 -209 35 -69 91 -127
  149 -154 98 -46 129 -32 303 143 141 141 248 268 364 430 73 102 75 105 233
  365 136 223 167 282 163 311 -5 34 -86 29 -161 -8 -80 -40 -125 -93 -293 -345
  -78 -118 -181 -262 -227 -320 -87 -109 -273 -294 -320 -319 -24 -13 -29 -12
  -74 27 -57 47 -251 246 -344 352 -40 45 -99 133 -149 220 -46 80 -101 171
  -122 203 -37 54 -38 60 -25 80 8 13 109 118 224 235 187 190 208 214 197 230
  -7 10 -22 56 -34 102 -28 108 -42 135 -81 149 -44 15 -60 14 -110 -9 -109 -49
  -219 0 -246 110 -12 52 -119 198 -212 290 -77 76 -112 104 -176 135 -94 46
  -178 57 -232 29z"
        />
        <path
          d="M5050 4421 c-33 -10 -108 -49 -143 -74 -41 -28 -135 -33 -269 -13
  -62 9 -123 18 -135 20 -12 2 -65 -1 -117 -5 -78 -6 -114 -16 -204 -53 -130
  -53 -205 -76 -248 -76 -17 0 -45 -7 -60 -15 -34 -18 -87 -19 -117 -4 -12 7
  -49 35 -82 64 -101 87 -133 94 -207 45 -70 -47 -224 -195 -272 -264 -80 -113
  -89 -137 -96 -278 -7 -134 -5 -128 -75 -224 -24 -33 -53 -166 -80 -367 -14
  -105 -28 -194 -31 -198 -2 -4 -20 -116 -39 -248 -64 -439 -133 -727 -224 -922
  -36 -80 -84 -237 -101 -336 -12 -68 -12 -74 6 -94 29 -32 89 -59 133 -59 71 0
  107 66 141 254 16 89 28 127 68 205 28 53 66 139 85 191 43 116 104 407 152
  727 55 366 95 590 121 675 8 26 33 48 55 48 10 0 59 -18 108 -40 99 -45 143
  -49 199 -21 20 10 49 21 65 25 36 9 101 68 122 111 28 60 47 175 46 284 0 57
  4 113 10 125 33 63 109 107 214 121 28 4 113 27 190 52 118 39 156 47 243 51
  56 2 102 1 102 -3 0 -4 -13 -46 -29 -94 -55 -164 -60 -282 -17 -386 12 -28 27
  -41 72 -62 79 -36 189 -41 362 -14 141 21 155 20 329 -15 98 -20 115 -39 141
  -150 58 -248 76 -477 69 -879 -4 -236 -10 -330 -27 -440 -33 -218 -106 -560
  -145 -679 -55 -169 -43 -257 40 -292 44 -18 69 -18 105 1 42 22 68 80 96 214
  14 64 33 143 44 176 11 33 31 114 45 180 14 66 36 161 49 210 35 130 48 331
  48 721 -1 363 -6 430 -52 672 -17 84 -30 158 -30 163 0 18 58 8 96 -17 21 -14
  82 -67 134 -118 52 -51 142 -132 200 -181 58 -49 231 -216 385 -370 154 -155
  294 -291 312 -303 17 -12 68 -66 112 -120 95 -114 134 -143 199 -150 45 -4 69
  7 612 280 311 157 598 297 638 312 100 38 178 93 263 184 93 100 102 114 94
  151 -11 50 -50 81 -105 81 -62 0 -161 -49 -198 -98 -69 -93 -131 -136 -269
  -188 -94 -36 -439 -206 -509 -251 -33 -22 -76 -43 -95 -47 -19 -3 -78 -31
  -130 -62 -138 -81 -178 -98 -234 -98 l-51 -1 -210 186 c-116 103 -241 224
  -279 268 -66 79 -291 284 -445 405 -109 87 -377 362 -405 417 -13 26 -34 95
  -46 155 -12 60 -28 123 -37 139 -46 91 -184 232 -286 294 -137 82 -319 128
  -406 102z"
        />
        <path
          d="M8355 4200 c-175 -12 -271 -60 -432 -214 -159 -152 -215 -258 -247
  -471 -11 -72 -38 -171 -77 -287 -33 -98 -61 -194 -62 -215 -2 -32 3 -40 32
  -60 92 -63 184 13 227 190 8 34 26 87 39 118 14 31 41 123 60 204 43 176 56
  210 110 295 76 119 176 200 295 240 46 16 99 23 199 27 186 8 442 -30 571 -84
  190 -80 260 -133 260 -199 0 -18 -14 -58 -31 -90 -29 -55 -31 -64 -27 -145 4
  -78 8 -94 51 -180 25 -52 61 -111 79 -133 50 -60 77 -68 231 -60 177 9 171 15
  238 -206 102 -336 151 -566 169 -794 25 -313 4 -502 -90 -806 -16 -52 -32
  -112 -35 -132 -3 -20 -30 -84 -60 -143 -55 -107 -64 -140 -45 -175 11 -20 59
  -40 97 -40 35 0 97 34 128 72 41 48 91 160 116 258 11 47 36 123 55 170 18 47
  45 133 59 190 24 101 25 117 25 420 0 406 -12 504 -92 764 -63 205 -58 298 18
  320 19 6 26 -5 69 -101 64 -147 108 -281 144 -448 40 -178 120 -417 256 -760
  97 -243 136 -333 209 -475 54 -106 190 -120 319 -32 23 16 76 49 117 72 82 48
  249 172 349 259 73 64 186 193 352 401 225 281 431 497 551 577 97 64 121 121
  78 191 -40 66 -116 89 -185 56 -44 -20 -356 -329 -516 -510 -68 -77 -188 -219
  -266 -315 -151 -187 -295 -333 -383 -390 -30 -19 -77 -50 -104 -67 -91 -59
  -118 -43 -175 104 -17 45 -40 89 -51 99 -11 10 -20 22 -20 28 0 5 -42 115 -94
  246 -93 232 -114 294 -176 518 -17 62 -62 201 -100 309 -86 244 -166 522 -210
  734 -27 129 -53 176 -167 306 -160 182 -221 224 -340 232 -84 5 -132 -9 -237
  -69 -72 -42 -126 -56 -168 -45 -13 3 -50 30 -83 61 -147 136 -380 196 -742
  193 -104 0 -233 -4 -288 -8z"
        />
        <path
          d="M11905 3880 c-27 -16 -61 -30 -74 -30 -30 0 -111 -46 -178 -102 -103
  -85 -173 -198 -194 -314 -14 -75 -5 -125 39 -213 34 -69 37 -80 36 -160 -1
  -71 -7 -105 -36 -191 -20 -58 -44 -152 -53 -210 -9 -58 -23 -148 -32 -200 -8
  -52 -22 -113 -30 -135 -9 -22 -18 -63 -20 -91 -12 -135 128 -239 233 -175 27
  17 33 27 39 73 3 29 15 103 25 163 11 61 31 189 44 285 35 244 67 340 134 403
  28 27 31 28 58 14 77 -39 134 -28 188 35 34 39 126 233 146 307 17 66 62 124
  121 156 50 28 61 30 154 29 197 -2 417 -54 587 -140 91 -46 320 -216 411 -307
  65 -65 68 -68 61 -105 -4 -20 -29 -91 -55 -158 -61 -150 -64 -198 -19 -267 48
  -71 104 -116 218 -170 l104 -50 -6 -161 c-8 -195 -29 -365 -56 -466 -11 -41
  -40 -160 -65 -265 -25 -104 -64 -244 -87 -310 -67 -197 -169 -448 -194 -478
  -73 -88 -7 -197 119 -197 111 0 146 46 238 315 33 99 79 231 100 293 133 385
  208 735 213 999 4 173 24 207 119 195 31 -3 50 3 110 39 157 93 237 204 237
  331 l0 68 38 0 c52 0 119 -34 280 -142 77 -51 192 -126 255 -166 63 -40 159
  -104 213 -143 113 -81 358 -219 454 -256 134 -51 277 -49 599 11 102 19 215
  37 251 41 36 3 137 20 225 38 132 26 185 31 302 32 130 0 143 2 157 20 19 26
  33 76 41 147 7 57 7 57 -26 77 -55 34 -137 28 -282 -19 -125 -40 -336 -77
  -572 -100 -59 -5 -165 -21 -236 -35 -77 -15 -164 -25 -215 -25 -77 0 -96 4
  -187 41 -132 52 -291 136 -392 207 -259 179 -431 287 -596 372 -272 141 -331
  190 -378 319 -31 85 -84 129 -217 181 -58 22 -109 40 -114 40 -5 0 -11 8 -15
  19 -10 33 -77 93 -129 117 -28 13 -104 37 -169 55 -145 39 -158 35 -216 -56
  -51 -81 -69 -92 -109 -62 -156 113 -182 128 -403 222 -217 93 -280 103 -649
  109 -205 3 -220 7 -372 112 -50 35 -99 64 -107 64 -9 0 -38 -14 -66 -30z"
        />
        <path
          d="M6120 3843 c-67 -35 -95 -71 -86 -109 17 -67 122 -120 266 -134 153
  -15 512 -104 655 -162 57 -23 124 -42 157 -45 53 -5 59 -4 87 25 25 24 31 39
  31 72 0 104 -63 143 -285 180 -38 6 -95 22 -125 36 -38 18 -105 33 -220 50
  -188 27 -280 52 -329 89 -45 32 -84 32 -151 -2z"
        />
        <path
          d="M17252 3754 c-191 -15 -230 -25 -401 -104 -71 -32 -158 -68 -194 -79
  -120 -37 -166 -66 -282 -184 l-110 -112 -65 7 c-36 4 -312 10 -615 14 -466 5
  -555 8 -584 21 -92 42 -261 -31 -261 -113 0 -47 50 -88 126 -102 134 -26 302
  -33 709 -31 386 3 423 2 453 -14 63 -34 71 -48 59 -108 -24 -122 30 -298 118
  -383 69 -68 156 -82 190 -32 22 30 19 52 -25 191 -64 202 -53 275 67 424 38
  46 79 99 93 117 20 26 61 49 192 108 211 96 284 120 469 156 186 37 352 40
  534 10 261 -43 517 -174 668 -340 137 -151 255 -488 328 -935 11 -71 32 -170
  45 -220 13 -49 37 -142 54 -205 38 -143 75 -350 95 -540 19 -180 35 -531 28
  -625 -3 -59 -1 -75 16 -103 43 -68 132 -93 185 -51 50 40 56 64 56 241 0 335
  -52 788 -125 1088 -25 102 -45 194 -45 205 0 11 -14 77 -30 145 -17 69 -53
  230 -81 358 -58 273 -63 288 -139 449 -113 237 -205 352 -385 479 -167 118
  -264 165 -450 219 -217 63 -382 74 -693 49z"
        />
        <path
          d="M4993 3095 c-40 -17 -77 -72 -104 -154 -27 -80 -55 -129 -232 -400
  -151 -234 -236 -376 -273 -459 -39 -88 -79 -128 -218 -219 -72 -48 -178 -128
  -235 -177 -212 -186 -266 -221 -451 -296 -69 -28 -147 -66 -175 -85 -52 -35
  -325 -146 -575 -233 -80 -28 -182 -64 -228 -81 -96 -36 -228 -61 -319 -61 -58
  0 -84 8 -236 71 -93 39 -207 80 -252 92 -71 18 -85 19 -114 8 -41 -17 -55 -46
  -75 -150 -10 -56 -23 -92 -37 -107 -13 -14 -64 -36 -138 -59 -197 -62 -232
  -77 -272 -115 -53 -50 -53 -85 2 -157 84 -112 202 -215 399 -348 203 -138 305
  -170 505 -160 166 8 236 24 360 84 255 122 318 195 352 404 l17 108 -39 80
  c-31 63 -37 83 -29 97 12 18 114 59 349 141 77 27 169 63 205 79 36 16 112 44
  170 62 148 45 197 63 302 116 152 76 273 152 391 246 139 110 250 185 344 233
  112 57 143 97 197 261 31 94 133 271 251 434 45 63 105 156 132 205 28 50 76
  130 107 180 91 145 113 225 81 301 -10 25 -26 40 -57 54 -51 23 -63 24 -105 5z"
        />
        <path
          d="M9092 2589 c2 -7 10 -15 17 -17 8 -3 12 1 9 9 -2 7 -10 15 -17 17 -8
  3 -12 -1 -9 -9z"
        />
        <path
          d="M7568 1943 c-32 -20 -41 -54 -63 -246 -9 -72 -24 -170 -35 -217 -10
  -47 -28 -130 -40 -185 -25 -114 -56 -187 -106 -246 -62 -75 -34 -144 69 -170
  78 -19 118 -2 161 69 70 115 132 312 156 496 6 45 24 138 41 207 16 70 29 142
  29 160 0 62 -19 109 -53 129 -40 25 -121 26 -159 3z"
        />
        <path
          d="M16352 1663 c-33 -16 -82 -99 -82 -137 0 -12 9 -45 20 -74 32 -85 43
  -189 30 -295 -15 -124 -68 -363 -91 -409 -48 -96 11 -188 120 -188 58 0 98 37
  106 98 3 26 22 130 42 232 34 172 37 201 40 415 4 225 4 231 -19 274 -39 76
  -110 111 -166 84z"
        />
      </g>
    </svg>
  );
}

export default Logo;
