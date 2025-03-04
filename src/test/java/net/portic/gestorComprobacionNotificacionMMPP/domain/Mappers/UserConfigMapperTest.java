package net.portic.gestorComprobacionNotificacionMMPP.domain.Mappers;

import net.portic.gestorComprobacionNotificacionMMPP.domain.dtos.UserConfigDto;
import net.portic.gestorComprobacionNotificacionMMPP.domain.mappers.UserConfigMapper;
import net.portic.gestorComprobacionNotificacionMMPP.domain.models.UserConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserConfigMapperTest {

    private final UserConfigMapper mapper = UserConfigMapper.INSTANCE;

    @Test
    void shouldMapDtoToEntity() {
        UserConfigDto userConfigDto = new UserConfigDto();
        userConfigDto.setUser("testUser");
        userConfigDto.setEmail("test@example.com");

        UserConfig result = mapper.fromDtoToEntity(userConfigDto);

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(userConfigDto.getUser());
        assertThat(result.getEmail()).isEqualTo(userConfigDto.getEmail());
    }

    @Test
    void shouldMapEntityToDto() {
        UserConfig userConfig = new UserConfig();
        userConfig.setUser("testUser");
        userConfig.setEmail("test@example.com");
        userConfig.setCompany(null);

        UserConfigDto result = mapper.fromEntityToDto(userConfig);

        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(userConfig.getUser());
        assertThat(result.getEmail()).isEqualTo(userConfig.getEmail());
    }
}
