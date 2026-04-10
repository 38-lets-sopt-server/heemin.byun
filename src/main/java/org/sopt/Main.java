package org.sopt;

import org.sopt.global.api.exception.BaseException;
import org.sopt.post.controller.PostController;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.global.api.response.ApiResponse;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 클라이언트는 Controller만 알면 돼요. Service도 Repository도 몰라도 돼요.
        PostController postController = new PostController();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== 에브리타임 게시판 ===");
            System.out.println("1. 게시글 작성");
            System.out.println("2. 전체 조회");
            System.out.println("3. 단건 조회");
            System.out.println("4. 게시글 수정");
            System.out.println("5. 게시글 삭제");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("내용: ");
                    String content = scanner.nextLine();
                    System.out.print("작성자: ");
                    String author = scanner.nextLine();

                    try {
                        ApiResponse<PostResponse> createResult = postController.createPost(
                                new CreatePostRequest(title, content, author)
                        );
                        System.out.println(createResult.getMessage());
                    } catch (BaseException e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 2:

                    ApiResponse<List<PostResponse>> allResult = postController.getAllPosts();
                    if (allResult.isSuccess()) {
                        allResult.getData().forEach(p -> System.out.println(p + "\n---"));
                    } else {
                        System.out.println(allResult.getMessage());
                    }
                    break;

                case 3:

                    System.out.print("조회할 게시글 ID: ");
                    ApiResponse<PostResponse> getResult = postController.getPost(scanner.nextLong());
                    scanner.nextLine();
                    if (getResult.isSuccess()) {
                        System.out.println(getResult.getData());
                    } else {
                        System.out.println(getResult.getMessage());
                    }
                    break;

                case 4:

                    System.out.print("수정할 게시글 ID: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine();
                    ApiResponse<PostResponse> checkResult = postController.getPost(updateId);
                    if (!checkResult.isSuccess()) {
                        System.out.println(checkResult.getMessage());
                        break;
                    }
                    System.out.print("새 제목: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("새 내용: ");
                    String newContent = scanner.nextLine();

                    try {
                        ApiResponse<PostResponse> updateResult = postController.updatePost(
                                updateId, new UpdatePostRequest(newTitle, newContent)
                        );
                        System.out.println(updateResult.getMessage());
                    } catch (BaseException e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 5:
                    System.out.print("삭제할 게시글 ID: ");
                    ApiResponse<Void> deleteResult = postController.deletePost(scanner.nextLong());
                    scanner.nextLine();
                    System.out.println(deleteResult.getMessage());
                    break;

                case 0:
                    running = false;
                    System.out.println("👋 프로그램 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다.");
            }
        }
        scanner.close();
    }
}