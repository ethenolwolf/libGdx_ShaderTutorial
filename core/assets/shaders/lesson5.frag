
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform float u_resolution;
uniform float u_radius;

uniform vec2 u_dir;

void main() {
	
	// for RGBA sum
	vec4 sum = vec4(0.0);
	
	// texture coordinate of this fragment
	vec2 tc = v_texCoords;
	
	// the amount to blur, i.e. how far off the center to sample from
	// 1.0 -> blur by one pixel
	// 2.0 -> blur by two pixels
	float blur = u_radius / u_resolution;
	
	// the direction to blur
	float hStep = u_dir.x;
	float vStep = u_dir.y;
	
	 // apply blur, using a 9-tap filter with predefined gaussian weights
	 sum += texture2D(u_texture, vec2(tc.x - 4.0 * blur * hStep, tc.y - 4.0 * blur * vStep)) * 0.0162162162;
	 sum += texture2D(u_texture, vec2(tc.x - 3.0 * blur * hStep, tc.y - 3.0 * blur * vStep)) * 0.0540540541;
	 sum += texture2D(u_texture, vec2(tc.x - 2.0 * blur * hStep, tc.y - 2.0 * blur * vStep)) * 0.1216216216;
	 sum += texture2D(u_texture, vec2(tc.x - 1.0 * blur * hStep, tc.y - 1.0 * blur * vStep)) * 0.1945945946;
	 
	 sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.2270270270;
	 
	 sum += texture2D(u_texture, vec2(tc.x + 1.0 * blur * hStep, tc.y + 1.0 * blur * vStep)) * 0.1945945946;
	 sum += texture2D(u_texture, vec2(tc.x + 2.0 * blur * hStep, tc.y + 2.0 * blur * vStep)) * 0.1216216216;
	 sum += texture2D(u_texture, vec2(tc.x + 3.0 * blur * hStep, tc.y + 3.0 * blur * vStep)) * 0.0540540541;
  	 sum += texture2D(u_texture, vec2(tc.x + 4.0 * blur * hStep, tc.y + 4.0 * blur * vStep)) * 0.0162162162;
  	 
  	 gl_FragColor = v_color * vec4(sum.rgb, 1.0);
}