import * as React from "react";
import * as PropTypes from "prop-types";
import {
  ColorPropType,
  Platform,
  processColor,
  requireNativeComponent,
  ViewPropTypes
} from "react-native";
import { LatLng } from "../types";
import { LatLngPropType } from "../prop-types";

export interface PolylineProps {
  /**
   * 节点坐标
   */
  coordinates: LatLng[];

  /**
   * 线段宽度
   */
  width?: number;

  /**
   * 线段颜色
   */
  color?: string;

  /**
   * 层级
   */
  zIndex?: number;

  /**
   * 多段颜色
   */
  colors?: string[];

  /**
   * 是否使用颜色渐变
   */
  gradient?: boolean;

  /**
   * 是否绘制大地线
   */
  geodesic?: boolean;

  /**
   * 是否绘制虚线
   */
  dashed?: boolean;
  /**
   * 虚线的类型
   */
  dashedLineType?: dashedLineTypes;
  dashedCapType?: dashedCapTypes
  dashedJoinType?: dashedJoinTypes

  /**
   * 点击事件
   */
  onPress?: () => void;
}

enum  dashedLineTypes {
  /**
   * 不画虚线
   */
  NONE = -1,
  /**
   * 圆点样式
   */
  CIRCLE = 0,
  /**
   * 方块样式
   */
  SUQUARE = 1,
}

enum  dashedCapTypes {
  /**
   * 普通头
   */
  LineCapButt = 0,
  /**
   * 扩展头
   */
  LineCapSquare,
  /**
   * 箭头
   */
  LineCapArrow,
  /**
   * 圆形头
   */
  LineCapRound,
}

enum  dashedJoinTypes {
  /**
   * 斜面连接点
   */
  LineJoinBevel = 0,
  /**
   * 斜接连接点
   */
  LineJoinMiter,
  /**
   *  圆角连接点
   */
  LineJoinRound,
}

/**
 * @ignore
 */
export default class Polyline extends React.PureComponent<PolylineProps> {
  static propTypes = {
    ...ViewPropTypes,
    coordinates: PropTypes.arrayOf(LatLngPropType).isRequired,
    width: PropTypes.number,
    color: ColorPropType,
    zIndex: PropTypes.number,
    colors: PropTypes.arrayOf(ColorPropType),
    gradient: PropTypes.bool,
    geodesic: PropTypes.bool,
    dashed: PropTypes.bool,
    dashedLineType: PropTypes.number,
    dashedCapType: PropTypes.number,
    dashedJoinType: PropTypes.number,
    onPress: PropTypes.func
  };

  static defaultProps = {
    colors: [],
    dashedLineType: dashedLineTypes.NONE,
    dashedCapType: dashedCapTypes.LineCapButt,
    dashedJoinType: dashedJoinTypes.LineJoinBevel,
  };

  render() {
    const props = {
      ...this.props,
      ...Platform.select({
        android: {
          colors: this.props.colors.map(processColor)
        }
      })
    };
    return <AMapPolyline {...props} />;
  }
}

// @ts-ignore
const AMapPolyline = requireNativeComponent("AMapPolyline", Polyline);
