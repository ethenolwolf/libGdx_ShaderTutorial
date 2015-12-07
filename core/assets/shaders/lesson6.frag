
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_normal;

void main(){ 

	vec3 normal = texture2D(u_normal, v_texCoords).rgb * 2.0 - 1.0;
	gl_FragColor = v_color * texture2D(u_texture, v_texCoords) * vec4(normal, 1.0);
}