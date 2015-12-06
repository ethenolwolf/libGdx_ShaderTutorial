
varying vec2 v_texCoords;
varying vec4 v_color;

uniform sampler2D u_texture;

void main() {
	vec4 texColor = texture2D(u_texture, v_texCoords);
	
	// invert the red, green, blue channels
	texColor.rgb = 1.0 - texColor.rbg;
	
	gl_FragColor = texColor * v_color;

}