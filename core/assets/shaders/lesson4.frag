#ifdef GL_ES
	precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_texture1;
uniform sampler2D u_mask;

void main() {
	// sample color from the first texture
	vec4 texColor0 = texture2D(u_texture, v_texCoords);
	
	// sample color from the second texture
	vec4 texColor1 = texture2D(u_texture1, v_texCoords);
	
	// get the mask; only use the alpha channel
	float mask = texture2D(u_mask, v_texCoords).a;
	
	// interpolate the colors bases on the mask
	gl_FragColor = v_color * mix(texColor0, texColor1, mask);
} 