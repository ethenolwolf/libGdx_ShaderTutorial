
varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_normal;

uniform vec2 u_resolution;
uniform vec3 u_lightPos;
uniform vec4 u_lightColor;
uniform vec4 u_ambientColor;
uniform vec3 u_fallOff;

void main(){ 

	vec4 diffuseColor = texture2D(u_texture, v_texCoords);
	vec3 normal = texture2D(u_normal, v_texCoords).rgb * 2.0 - 1.0;

	vec3 lightDir = vec3(u_lightPos.xy - (gl_FragCoord.xy / u_resolution.xy), u_lightPos.z);
	
	// correct for aspect ratio
	lightDir.x *= u_resolution.x / u_resolution.y;
	
	// the distance 
	float D = length(lightDir);
	
	// normalize the vectors
	vec3 N = normalize(normal);
	vec3 L = normalize(lightDir);
	
	// pre-multiply light color with intensity
	// then perform N dot L to determine the diffuse term
	vec3 diffuse = (u_lightColor.rgb * u_lightColor.a) * max(dot(N, L), 0.0);
	
	// pre-multiply ambient color with intensity
	vec3 ambient = u_ambientColor.rgb * u_ambientColor.a;
	
	// calculate the attenuation
	float attenuation = 1.0 / (u_fallOff.x + (u_fallOff.y * D) + (u_fallOff.z * D * D)); 
	
	vec3 intensity = ambient + diffuse * attenuation;
	vec3 finalColor = diffuseColor.rgb * intensity;

	gl_FragColor = v_color * vec4(finalColor, diffuseColor.a);
}