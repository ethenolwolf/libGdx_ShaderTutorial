
uniform sampler2D u_texture;

// screen resolution
uniform vec2 u_resolution;

varying vec4 v_color;
varying vec2 v_texCoords;


// radius of vignette, where 0.5 results in a circle fitting the screen
const float RADIUS = 0.75;

// softness of vignette, between 0.0 and 1.0
const float SOFTNESS = 0.45;

// sepia color
const vec3 SEPIA = vec3(1.2, 1.0, 0.8);

void main() {
	vec4 texColor = texture2D(u_texture, v_texCoords);
	
	// 1. Vignette
	
	// determine the center position
	vec2 position = (gl_FragCoord.xy / u_resolution.xy) - vec2(0.5);
	
	// determine the vector length from the center position
	float len = length(position);
	
	// use smoothstep to create a smooth vignette
	float vignette = smoothstep(RADIUS, RADIUS - SOFTNESS, len);
	
	// apply the vignette with 50% opacity
	texColor.rgb = mix(texColor.rgb, texColor.rgb * vignette, 0.5);
	
	// 2. Grayscale
	
	float gray = dot(texColor.rgb, vec3(0.299, 0.587, 0.114));
	
	// 3. Sepia
	
	vec3 sepiaColor = vec3(gray) * SEPIA;
	
	// mix the color with sepia effect at 75%
	
	texColor.rgb = mix(texColor.rgb, sepiaColor, 0.75);
	
	gl_FragColor = texColor * v_color; 
}
