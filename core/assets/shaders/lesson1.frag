#ifdef GL_ES
#define LOWP lowp
	precision mediump float;
#else
	#define LOWP
#endif

varying vec2 v_texCoords;

uniform sampler2D u_texture;

void main() {
	gl_FragColor = vec4(1.0, 0.5, 0.5, 1.0) * texture2D(u_texture, v_texCoords);
}