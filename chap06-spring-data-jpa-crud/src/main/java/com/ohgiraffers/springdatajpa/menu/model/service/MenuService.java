package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    // Bean 으로 등록한 modelMapper 의존성 주입
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    /* 1. 메뉴 코드로 특정 메뉴 조회하기  */
    public MenuDTO findMenuByMenuCode(int menuCode) {

        Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); // 잘못된 파라미터가 넘겨왔을 때 예외처리 필수

        // entity -> dto
        // map(변환 대상, 변환 할 타입)
        return modelMapper.map(foundMenu, MenuDTO.class);
    }

    /* 페이징 처리하지 않은 메뉴 리스트 조회하기 */
    public List<MenuDTO> findMenuList() {

//        List<Menu> menuList = menuRepository.findAll(); 정렬 없는 findAll
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuPrice").descending());

        // stream : 컬렉션(List 등등) 데이터를 처리하기 위해 나열
        return menuList.stream()
                // map : 스트림화 된 데이터를 꺼내 매핑 및 변환
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                // collect : 스트림화 된 데이터를 다시 List 로 변환
                .collect(Collectors.toList());
    }

    /* 페이지 처리를 한 메뉴 전체 조회 */
    public Page<MenuDTO> findMenuListByPaging(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(
                menu -> modelMapper.map(menu, MenuDTO.class)
        );
    }

    public List<MenuDTO> findByMenuPrice(int menuPrice) {

        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        return menuList.stream().map(
                menu -> modelMapper.map(menu, MenuDTO.class)
        ).collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {

        /* 여기서는 직접 쿼리문을 우리가 작성해보자 */
        List<Category> categoryList = categoryRepository.findAllCategory();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // DML 구문이기 때문에 @Transaction
    @Transactional
    public void registNewMenu(MenuDTO newMenu) {

        // 지금까지는 Entity 를 DTO 로 변환했다면
        // DML 구문에서는 DTO 타입을 Entity 로 변환을 해야
        // Persistence Context == JPA 가 관리를 해준다.
        menuRepository.save(modelMapper.map(newMenu, Menu.class));

    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {

        /* update 는 엔티틸ㄹ 특정해서 필드의 값을 변경해주면 된다. */
        /* JPA 는 변경감지 기능이 있다.
        *   따라서 하나의 엔티리를 특정해서 필드 값을 변경하면
        *   변경된 값으로 flush (반영) 을 해준다.
        * */

        // 엔티티 찾기(특정)
        Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode()).orElseThrow(IllegalArgumentException::new); // 잘못된 파라미터가 넘겨왔을 때 예외처리 필수

        System.out.println("특정된 메뉴 foundMenu = " + foundMenu);

        /* 1. Setter 를 통해 update 기능 - (지양한다.) */
//        foundMenu.setMenuName(modifyMenu.getMenuName());
//
//        System.out.println("setter 사용 후 foundMenu = " + foundMenu);

        /* 2. @Builder 를 통해 update 기능 */
//        foundMenu = foundMenu.toBuilder().menuName(modifyMenu.getMenuName()).build();
//
//        // build 를 통해서 foundMenu 새롭게 탄생 시켰으니
//        // save 메소드를 통해 JPA 전달
//
//        menuRepository.save(foundMenu);
//
//        System.out.println("빌드를 통해 업데이트 값 확인 : " + foundMenu);

        /* 3. Entity 내부에 Builder 패턴을 구현 */
        foundMenu = foundMenu.menuName(modifyMenu.getMenuName()).builder();

        menuRepository.save(foundMenu);
    }

    @Transactional
    public void deleteMenu(int menuCode) {

        menuRepository.deleteById(menuCode);
    }
}
