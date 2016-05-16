
## opengl 光照模型
### 面法向量和顶点法向量
* 三角形法向  
三角形法向:一个三角形的法向是一个长度为1并且垂直于这个三角形的向量。通过简单地将三角形两条边进行叉乘计算（向量a和b的叉乘结果是一个同时垂直于a和b的向量，记得？），然后归一化：使长度为1。伪代码如下：  

``` cpp
triangle ( v1, v2, v3 )
edge1 = v2-v1
edge2 = v3-v1
triangle.normal = cross(edge1, edge2).normalize()
```  
* 顶点法向量  
顶点的法向量:是包含该顶点的所有三角形的法向的均值。这很方便——因为在顶点着色器中，我们处理顶点，而不是三角形；所以在顶点处有信息是很好的。并且在OpenGL中，我们没有任何办法获得三角形信息。伪代码如下：

```cpp
vertex v1, v2, v3, ....
triangle tr1, tr2, tr3 // all share vertex v1
v1.normal = normalize( tr1.normal + tr2.normal + tr3.normal )
```

### 环境光 Ambient
环境光只和光源的颜色和环境光的强度有关.  
包含的变量记为: ambientColor  ambientIntensity
### 漫反射 Diffuse
漫反射依赖于光源的方向,而且还和物体表面的法向量有关.
光源反射的方向和法向量的夹角越小,物体反射的光越强.  
如果光线垂直于表面，它会聚在一小片表面上。如果它以一个倾斜角到达表面，相同的强度光照亮更大一片表面：
![如图](http://www.tairan.com/usr/uploads/2014/04/diffuseAngle.png)  

这意味着在斜射下，表面的点会较黑（但是记住，更多的点会被照射到，总光强度仍然是一样的）
也就是说，当计算像素的颜色时，入射光和表面法向的夹角很重要。因此有：

```cpp
// Cosine of the angle between the normal and the light direction,
// clamped above 0
//  - light is at the vertical of the triangle -> 1
//  - light is perpendicular to the triangle -> 0
float cosTheta = dot( n,l );
color = LightColor * cosTheta;
```

*注意正负*

求cosTheta的公式有漏洞。如果光源在三角形后面，n和l方向相反，那么n.l是负值。这意味着colour=一个负数，没有意义。因此这种情况须用clamp()将cosTheta赋值为0：  

```cpp
// Cosine of the angle between the normal and the light direction,
// clamped above 0
//  - light is at the vertical of the triangle -> 1
//  - light is perpendicular to the triangle -> 0
//  - light is behind the triangle -> 0
float cosTheta = clamp( dot( n,l ), 0,1 );
color = LightColor * cosTheta;
```

*材质颜色*
当然，输出颜色也依赖于材质颜色。在这幅图像中，白光由绿、红、蓝光组成。当光碰到红色材质时，绿光和蓝光被吸收，只有红光保留着。  
![如图](http://www.tairan.com/usr/uploads/2014/04/diffuseRed.png)  
我们可以通过一个简单的乘法来模拟：  

```cpp
color = MaterialDiffuseColor * LightColor * cosTheta;
```

*模拟光源*
首先假设在空间中有一个点光源，它向所有方向发射光线，像蜡烛一样。  
对于该光源，我们的表面收到的光通量依赖于表面到光源的距离：越远光越少。实际上，光通量与距离的平方成反比：  

```cpp
color = MaterialDiffuseColor * LightColor * cosTheta / (distance*distance);
```

最后，需要另一个参数来控制光的强度。  
```cpp
color = MaterialDiffuseColor * LightColor * LightPower * cosTheta / (distance*distance);
```
