package com.bhaskar.utils;

public enum AviLoaderIndicator {

    BallPulseIndicator,
    BallGridPulseIndicator,
    BallClipRotateIndicator,
    BallClipRotatePulseIndicator,
    SquareSpinIndicator,
    BallClipRotateMultipleIndicator,
    BallPulseRiseIndicator,
    BallRotateIndicator,
    CubeTransitionIndicator,
    BallZigZagIndicator,
    BallZigZagDeflectIndicator,
    BallTrianglePathIndicator,
    BallScaleIndicator,
    LineScaleIndicator,
    LineScalePartyIndicator,
    BallScaleMultipleIndicator,
    BallPulseSyncIndicator,
    BallBeatIndicator,
    LineScalePulseOutIndicator,
    LineScalePulseOutRapidIndicator,
    BallScaleRippleIndicator,
    BallScaleRippleMultipleIndicator,
    BallSpinFadeLoaderIndicator,
    LineSpinFadeLoaderIndicator,
    TriangleSkewSpinIndicator,
    PacmanIndicator,
    BallGridBeatIndicator,
    SemiCircleSpinIndicator;

    /***
     *
     * @param indicator
     * @return
     */
    public static String getAviLoader(AviLoaderIndicator indicator) {
        return AviLoaderType[indicator.ordinal()];
    }

    private static final String[] AviLoaderType = {
            "BallPulseIndicator",
            "BallGridPulseIndicator",
            "BallClipRotateIndicator",
            "BallClipRotatePulseIndicator",
            "SquareSpinIndicator",
            "BallClipRotateMultipleIndicator",
            "BallPulseRiseIndicator",
            "BallRotateIndicator",
            "CubeTransitionIndicator",
            "BallZigZagIndicator",
            "BallZigZagDeflectIndicator",
            "BallTrianglePathIndicator",
            "BallScaleIndicator",
            "LineScaleIndicator",
            "LineScalePartyIndicator",
            "BallScaleMultipleIndicator",
            "BallPulseSyncIndicator",
            "BallBeatIndicator",
            "LineScalePulseOutIndicator",
            "LineScalePulseOutRapidIndicator",
            "BallScaleRippleIndicator",
            "BallScaleRippleMultipleIndicator",
            "BallSpinFadeLoaderIndicator",
            "LineSpinFadeLoaderIndicator",
            "TriangleSkewSpinIndicator",
            "PacmanIndicator",
            "BallGridBeatIndicator",
            "SemiCircleSpinIndicator" };

}
